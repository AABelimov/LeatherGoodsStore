package ru.aabelimov.leathergoodsstore.dto;

public record CreateOrUpdateProductDto(String name, String price, String description, Long categoryId) {
}
