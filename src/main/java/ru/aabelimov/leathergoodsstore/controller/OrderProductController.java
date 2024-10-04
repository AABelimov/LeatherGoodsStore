package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderProductDto;
import ru.aabelimov.leathergoodsstore.service.OrderService;

@Controller
@RequestMapping("order-products")
@RequiredArgsConstructor
public class OrderProductController {

    private final OrderService orderService;

    @PostMapping
    public String createOrderProduct(CreateOrderProductDto dto) {
        orderService.createOrderProduct(dto);
        return "redirect:/orders/%d".formatted(dto.orderId());
    }
}
