package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aabelimov.leathergoodsstore.entity.*;
import ru.aabelimov.leathergoodsstore.repository.OrderProductRepository;
import ru.aabelimov.leathergoodsstore.service.OrderProductService;

import java.util.List;
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
    public OrderProduct getOrderProduct(Long id) {
        return orderProductRepository.findById(id).orElseThrow(); // TODO ::
    }

    @Override
    public List<OrderProduct> getAllByOrderId(Long orderId) {
        return orderProductRepository.findAllByOrderId(orderId);
    }

    @Override
    public void updateQuantity(OrderProduct orderProduct, String operator) {
        switch (operator) {
            case "plus" -> {
                orderProduct.setQuantity(orderProduct.getQuantity() + 1);
            }
            case "minus" -> {
                orderProduct.setQuantity(orderProduct.getQuantity() - 1);
            }
        }
        orderProductRepository.save(orderProduct);
    }

    @Override
    public void deleteOrderProduct(OrderProduct orderProduct) {
        orderProductRepository.delete(orderProduct);
    }
}
