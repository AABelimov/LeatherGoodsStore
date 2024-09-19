package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.dto.UpdatedOrderProductQuantityDto;
import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.OrderStatus;

import java.util.List;

public interface OrderService {

    Order createOrder(CreateOrderDto dto);

    Order getOrder(Long id);

    List<Order> getOrdersByStatus(OrderStatus status);

    UpdatedOrderProductQuantityDto updateOrderProductQuantity(Long orderProductId, String operator);

    UpdatedOrderProductQuantityDto deleteOrderProduct(Long orderProductId);

    void deleteOrder(Long id);
}
