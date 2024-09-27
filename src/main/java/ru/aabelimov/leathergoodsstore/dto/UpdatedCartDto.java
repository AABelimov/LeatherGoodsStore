package ru.aabelimov.leathergoodsstore.dto;

public record UpdatedCartDto(Long productLeatherColorId, Integer quantity, Integer cost, Double totalCost, Integer totalQuantity) {
}
