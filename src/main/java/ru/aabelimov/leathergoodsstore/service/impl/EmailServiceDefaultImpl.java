package ru.aabelimov.leathergoodsstore.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.PaymentResponse;
import ru.aabelimov.leathergoodsstore.service.EmailService;
import ru.aabelimov.leathergoodsstore.service.OrderProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceDefaultImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final OrderProductService orderProductService;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${admin.username}")
    private String username;

    @Override
    public void sendNotificationAdminAboutNewOrder(Order order, List<Image> productsImages) {
        List<String> imagesResourceNames = productsImages.stream()
                .map(Image::getImagePath)
                .toList();
        Context context = new Context();
        String subject = "Оформлен новый заказ";

        context.setVariable("subject", subject);
        context.setVariable("order", order);
        context.setVariable("orderProducts", orderProductService.getAllByOrderId(order.getId()));
        context.setVariable("imagesResourceNames", imagesResourceNames);

        Thread sendMessageThread = new Thread(() -> {
            try {
                sendHtmlMessage(username, subject, "email/notification-admin-about-new-order", context, productsImages);
            } catch (MessagingException e) {
                throw new RuntimeException(e); // TODO ::
            }
        });

        sendMessageThread.start();
    }

    @Override
    public void sendPaymentOrder(Order order, PaymentResponse paymentResponse, List<Image> productsImages) {
        List<String> imagesResourceNames = productsImages.stream()
                .map(Image::getImagePath)
                .toList();
        Context context = new Context();
        String subject = "Детали вашего заказа";

        context.setVariable("subject", subject);
        context.setVariable("order", order);
        context.setVariable("orderProducts", orderProductService.getAllByOrderId(order.getId()));
        context.setVariable("imagesResourceNames", imagesResourceNames);
        context.setVariable("paymentLink", paymentResponse.getConfirmation().getConfirmation_url());

        Thread sendMessageThread = new Thread(() -> {
            try {
                sendHtmlMessage(order.getUser().getUsername(), subject, "email/payment-order", context, productsImages);
            } catch (MessagingException e) {
                throw new RuntimeException(e); // TODO ::
            }
        });

        sendMessageThread.start();
    }

    private synchronized void sendHtmlMessage(String to, String subject, String template, Context context, List<Image> productsImages) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        String htmlContent = templateEngine.process(template, context);

        helper.setFrom(mailUsername);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        productsImages.forEach(image -> {
            try {
                InputStreamSource imageSource = new ByteArrayResource(Files.readAllBytes(Path.of(image.getImagePath())));
                helper.addInline(image.getImagePath(), imageSource, image.getMediaType());
            } catch (IOException | MessagingException e) {
                throw new RuntimeException(e); // TODO ::
            }
        });

        emailSender.send(mimeMessage);
    }
}
