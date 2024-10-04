package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.entity.*;

import java.util.List;

public interface OrderProductService {

    void createOrderProduct(Order order, ProductLeatherColor plc);

    void createOrderProducts(Order order);

    OrderProduct getOrderProduct(Long id);

    List<OrderProduct> getAllByOrderId(Long orderId);

    void updateQuantity(OrderProduct orderProduct, String operator);

    void deleteOrderProduct(OrderProduct orderProduct);
}
