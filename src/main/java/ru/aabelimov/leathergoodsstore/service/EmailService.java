package ru.aabelimov.leathergoodsstore.service;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void sendHtmlMessage(String to, String subject, String template, Context context) throws MessagingException;
}
