package ru.aabelimov.leathergoodsstore.service;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;
import ru.aabelimov.leathergoodsstore.entity.Image;

import java.util.List;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void sendHtmlMessage(String to, String subject, String template, Context context, List<Image> productsImages) throws MessagingException;
}
