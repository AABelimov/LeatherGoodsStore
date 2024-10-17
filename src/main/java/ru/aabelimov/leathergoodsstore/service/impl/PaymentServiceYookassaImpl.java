package ru.aabelimov.leathergoodsstore.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.PaymentRequest;
import ru.aabelimov.leathergoodsstore.entity.PaymentResponse;
import ru.aabelimov.leathergoodsstore.service.EmailService;
import ru.aabelimov.leathergoodsstore.service.OrderService;
import ru.aabelimov.leathergoodsstore.service.PaymentService;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceYookassaImpl implements PaymentService {

    private final OrderService orderService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @Value("${credentials.yookassa.shop-id}")
    private String shopId;

    @Value("${credentials.yookassa.secret-key}")
    private String secretKey;

    @Override
    public void createPayment(Order order) throws JsonProcessingException {
        PaymentRequest paymentRequest = new PaymentRequest(BigDecimal.valueOf(order.getTotalCost()), "Заказ №%d".formatted(order.getId()));
        String credentials = "%s:%s".formatted(shopId, secretKey);
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = null;
        PaymentResponse paymentResponse = null;
        List<Image> productsImages = orderService.getProductsImagesFromOrder(order.getId());

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Idempotence-Key", "Order #%d".formatted(order.getId()));
        headers.add("Authorization", "Basic " + encodedCredentials);

        request = new HttpEntity<>(objectMapper.writeValueAsString(paymentRequest), headers);
        paymentResponse = restTemplate.postForObject("https://api.yookassa.ru/v3/payments", request, PaymentResponse.class);

        if (paymentResponse != null) {
            emailService.sendPaymentOrder(order, paymentResponse, productsImages);
            orderService.updatePaymentId(order, paymentResponse.getId());
        }

    }
}
