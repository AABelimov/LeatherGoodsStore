package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.dto.CreateProductLeatherColorDto;
import ru.aabelimov.leathergoodsstore.dto.UpdateCartDto;
import ru.aabelimov.leathergoodsstore.dto.UpdatedCartDto;
import ru.aabelimov.leathergoodsstore.entity.Cart;

public interface CartService {

    void addNewProductToCart(CreateProductLeatherColorDto dto);

    UpdatedCartDto updateCart(UpdateCartDto dto);

    void clearCart();
}
