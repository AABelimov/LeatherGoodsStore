package ru.aabelimov.leathergoodsstore.dto;

public record CreateOrderProductDto(Long orderId, Long productId, Long leatherColorId) {
}
