package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.OrderProduct;

import java.util.List;

public interface OrderProductService {

    void createOrderProducts(Order order);

    OrderProduct getOrderProduct(Long id);

    List<OrderProduct> getAllByOrderId(Long orderId);

    void updateQuantity(OrderProduct orderProduct, String operator);

    void deleteOrderProduct(OrderProduct orderProduct);
}
