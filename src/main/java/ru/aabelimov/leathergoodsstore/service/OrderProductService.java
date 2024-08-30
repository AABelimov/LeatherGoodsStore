package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.OrderProduct;

public interface OrderProductService {

    void createOrderProducts(Order order);

    void deleteOrderProduct(OrderProduct orderProduct);
}
