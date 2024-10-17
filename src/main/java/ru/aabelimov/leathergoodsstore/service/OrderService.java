package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderProductDto;
import ru.aabelimov.leathergoodsstore.dto.UpdatedOrderProductQuantityDto;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.OrderStatus;

import java.util.List;

public interface OrderService {

    void createOrder(CreateOrderDto dto);

    void createOrderProduct(CreateOrderProductDto dto);

    Order getOrder(Long id);

    List<Order> getOrdersByStatus(OrderStatus status);

    List<Image> getProductsImagesFromOrder(Long id);

    UpdatedOrderProductQuantityDto updateOrderProductQuantity(Long orderProductId, String operator);

    UpdatedOrderProductQuantityDto deleteOrderProduct(Long orderProductId);

    void updateStatus(Long id, OrderStatus status);

    void updatePaymentId(Order order, String id);

    void deleteOrder(Long id);
}
