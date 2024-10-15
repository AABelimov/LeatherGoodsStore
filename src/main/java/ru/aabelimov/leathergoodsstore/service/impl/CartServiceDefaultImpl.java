package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aabelimov.leathergoodsstore.dto.CreateProductLeatherColorDto;
import ru.aabelimov.leathergoodsstore.dto.UpdateCartDto;
import ru.aabelimov.leathergoodsstore.dto.UpdatedCartDto;
import ru.aabelimov.leathergoodsstore.entity.*;
import ru.aabelimov.leathergoodsstore.service.CartService;
import ru.aabelimov.leathergoodsstore.service.LeatherColorService;
import ru.aabelimov.leathergoodsstore.service.ProductLeatherColorService;
import ru.aabelimov.leathergoodsstore.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceDefaultImpl implements CartService {

    private final ProductService productService;
    private final LeatherColorService leatherColorService;
    private final ProductLeatherColorService productLeatherColorService;
    private final Cart cart;

    @Override
    public void addNewProductToCart(CreateProductLeatherColorDto dto) {
        Product product = productService.getProduct(dto.productId());
        LeatherColor leatherColor = leatherColorService.getLeatherColor(dto.leatherColorId());
        ProductLeatherColor plc = productLeatherColorService.getProductLeatherColorByProductAndLeatherColor(product, leatherColor);
        Map<ProductLeatherColor, Integer> products = cart.getProducts();

        if (plc == null) {
            plc = productLeatherColorService.createProductLeatherColor(product, leatherColor);
        }

        if (products.containsKey(plc)) {
            products.put(plc, products.get(plc) + 1);
        } else {
            products.put(plc, 1);
        }

        cart.setTotalCost(cart.getTotalCost() + plc.getProduct().getPrice());
        cart.setTotalQuantity(cart.getTotalQuantity() + 1);
    }

    @Override
    public List<Image> getProductsImages() {
        List<ProductLeatherColor> plcList = cart.getProducts().keySet().stream().toList();
        List<Image> images = new ArrayList<>();
        plcList.forEach(plc -> {
            if (!plc.getImages().isEmpty()) {
                images.add(plc.getImages().get(0));
            } else {
                images.add(plc.getProduct().getImages().get(0));
            }
        });
        return images;
    }

    @Override
    public UpdatedCartDto updateCart(UpdateCartDto dto) {
        ProductLeatherColor plc = productLeatherColorService.getProductLeatherColor(dto.productLeatherColorId());

        if (cart.getProducts().containsKey(plc)) {
            int totalQuantity = 0;
            int quantity = 0;
            int cost = 0;

            switch (dto.operation()) {
                case "plus" -> {
                    totalQuantity = cart.getTotalQuantity() + 1;
                    quantity = cart.getProducts().get(plc) + 1;
                    cost += plc.getProduct().getPrice();
                }
                case "minus" -> {
                    totalQuantity = cart.getTotalQuantity() - 1;
                    quantity = cart.getProducts().get(plc) - 1;
                    cost -= plc.getProduct().getPrice();
                }
                case "remove" -> {
                    totalQuantity = cart.getTotalQuantity() - cart.getProducts().get(plc);
                    cost = plc.getProduct().getPrice() * cart.getProducts().get(plc) * -1;
                }
                default -> throw new RuntimeException(); // TODO :: add exception
            }

            if (quantity > 0) {
                cart.getProducts().put(plc, quantity);

            } else {
                cart.getProducts().remove(plc);
            }

            cart.setTotalQuantity(totalQuantity);
            cart.setTotalCost(cart.getTotalCost() + cost);
            return new UpdatedCartDto(plc.getId(), quantity, Math.abs(cost) * quantity, cart.getTotalCost(), totalQuantity);
        }

        throw new RuntimeException("not fount that product in the cart"); // TODO: add exception
    }

    @Override
    public void clearCart() {
        cart.setProducts(new HashMap<>());
        cart.setTotalQuantity(0);
        cart.setTotalCost(0.0);
    }
}
