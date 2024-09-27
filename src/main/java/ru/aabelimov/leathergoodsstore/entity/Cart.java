package ru.aabelimov.leathergoodsstore.entity;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@SessionScope
public class Cart {

    private Map<ProductLeatherColor, Integer> products = new HashMap<>();
    private Integer totalQuantity = 0;
    private Double totalCost = 0.0;
}
