package ru.aabelimov.leathergoodsstore.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.OrderStatus;

import java.time.LocalDateTime;

@Component
public class OrderMapper {

    public Order toEntity(CreateOrderDto dto) {
        Order order = new Order();
        order.setComment(dto.comment());
        order.setCommunicationType(dto.communicationType());
        order.setCommunicationData(dto.communicationData());
        order.setDeliveryMethod(dto.deliveryMethod());
        order.setFio(dto.fio());
        order.setAddress(dto.address());
        order.setStatus(OrderStatus.NEW);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
}
