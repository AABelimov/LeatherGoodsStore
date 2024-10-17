package ru.aabelimov.leathergoodsstore.service;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.PaymentResponse;

import java.util.List;

public interface EmailService {

    void sendNotificationAdminAboutNewOrder(Order order, List<Image> productsImages);

    void sendPaymentOrder(Order order, PaymentResponse paymentResponse, List<Image> productsImages);
}
