package ru.aabelimov.leathergoodsstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.aabelimov.leathergoodsstore.entity.Order;

public interface PaymentService {

    void createPayment(Order order) throws JsonProcessingException;
}
