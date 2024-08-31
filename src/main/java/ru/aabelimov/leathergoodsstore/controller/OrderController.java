package ru.aabelimov.leathergoodsstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.entity.*;
import ru.aabelimov.leathergoodsstore.service.CategoryService;
import ru.aabelimov.leathergoodsstore.service.EmailService;
import ru.aabelimov.leathergoodsstore.service.OrderService;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CategoryService categoryService;
    private final EmailService emailService;
    private final Cart cart;
    private final ObjectMapper objectMapper;

    @Value("${credentials.yookassa.shop-id}")
    private String shopId;

    @Value("${credentials.yookassa.secret-key}")
    private String secretKey;

    @PostMapping
    public String createOrder(CreateOrderDto dto) throws JsonProcessingException {
        Order order = orderService.createOrder(dto);

//        PaymentRequest paymentRequest = new PaymentRequest(BigDecimal.valueOf(order.getTotalCost()),
//                "Заказ №%d".formatted(order.getId()));
//        String credentials = "%s:%s".formatted(shopId, secretKey);
//        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Idempotence-Key", "%d".formatted(order.getId()));
//        headers.add("Authorization", "Basic " + encodedCredentials);
//        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(paymentRequest), headers);
//        PaymentResponse test = restTemplate.postForObject("https://api.yookassa.ru/v3/payments", request, PaymentResponse.class);
//        System.out.println(test);
//
//        emailService.sendSimpleMessage(order.getUser().getUsername(),
//                "Оплата заказа #%d".formatted(order.getId()),
//                "Оплати заказ по ссылке: %s".formatted(Objects.requireNonNull(test).getConfirmation().getConfirmation_url()));

//        System.out.println(payment);
        return "redirect:/";
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrder(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "order/order";
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getOrdersByStatus(OrderStatus.NEW));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "order/orders";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
