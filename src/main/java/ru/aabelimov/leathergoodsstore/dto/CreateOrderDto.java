package ru.aabelimov.leathergoodsstore.dto;

import ru.aabelimov.leathergoodsstore.entity.CommunicationType;

public record CreateOrderDto(String username, String name, String phoneNumber, String comment, String communicationType, String communicationData, String address) {
}
