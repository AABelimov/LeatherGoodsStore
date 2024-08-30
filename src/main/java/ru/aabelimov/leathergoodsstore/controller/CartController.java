package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.aabelimov.leathergoodsstore.dto.CreateProductLeatherColorDto;
import ru.aabelimov.leathergoodsstore.dto.UpdateCartDto;
import ru.aabelimov.leathergoodsstore.dto.UpdatedCartDto;
import ru.aabelimov.leathergoodsstore.service.CartService;

@Controller
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PatchMapping
    public String addNewProductToCart(CreateProductLeatherColorDto dto) {
        cartService.addNewProductToCart(dto);
        return "redirect:/products";
    }

    @PatchMapping("update-cart")
    @ResponseBody
    public UpdatedCartDto updateCart(@RequestBody UpdateCartDto dto) {
        return cartService.updateCart(dto);
    }
}
