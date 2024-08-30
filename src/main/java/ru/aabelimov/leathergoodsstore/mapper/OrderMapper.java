package ru.aabelimov.leathergoodsstore.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.entity.CommunicationType;
import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.OrderStatus;

@Component
public class OrderMapper {

    public Order toEntity(CreateOrderDto dto) {
        Order order = new Order();
        order.setComment(dto.comment());
        order.setCommunicationType(CommunicationType.valueOf(dto.communicationType().toUpperCase()));
        order.setCommunicationData(dto.communicationData());
        order.setAddress(dto.address());
        order.setStatus(OrderStatus.NEW);
        return order;
    }
}
