package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aabelimov.leathergoodsstore.entity.Cart;
import ru.aabelimov.leathergoodsstore.entity.Order;
import ru.aabelimov.leathergoodsstore.entity.OrderProduct;
import ru.aabelimov.leathergoodsstore.entity.ProductLeatherColor;
import ru.aabelimov.leathergoodsstore.repository.OrderProductRepository;
import ru.aabelimov.leathergoodsstore.service.OrderProductService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderProductServiceDefaultImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final Cart cart;

    @Override
    public void createOrderProducts(Order order) {
        Map<ProductLeatherColor, Integer> products = cart.getProducts();
        for (ProductLeatherColor plc : products.keySet()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProductLeatherColor(plc);
            orderProduct.setQuantity(products.get(plc));
            orderProductRepository.save(orderProduct);
        }
    }

    @Override
    public void deleteOrderProduct(OrderProduct orderProduct) {
        orderProductRepository.delete(orderProduct);
    }
}
