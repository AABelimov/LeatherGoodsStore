package ru.aabelimov.leathergoodsstore.dto;

public record CreateOrderDto(String username, String name, String phoneNumber, String comment, String communicationType, String communicationData, String deliveryMethod, String fio, String address, String promoCode) {
}
