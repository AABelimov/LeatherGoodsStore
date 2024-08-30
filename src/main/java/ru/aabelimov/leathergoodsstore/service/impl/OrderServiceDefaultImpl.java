package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.entity.*;
import ru.aabelimov.leathergoodsstore.mapper.OrderMapper;
import ru.aabelimov.leathergoodsstore.repository.OrderRepository;
import ru.aabelimov.leathergoodsstore.service.CartService;
import ru.aabelimov.leathergoodsstore.service.OrderProductService;
import ru.aabelimov.leathergoodsstore.service.OrderService;
import ru.aabelimov.leathergoodsstore.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceDefaultImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final OrderProductService orderProductService;
    private final CartService cartService;
    private final Cart cart;

    @Override
    @Transactional
    public Order createOrder(CreateOrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        User user = userService.getUserByUsername(dto.username());
        if (user == null) {
            user = userService.createUser(dto);
        }
        order.setUser(user);
        order.setTotalCost(cart.getTotalCost());
        order.setTotalQuantity(cart.getTotalQuantity());
        order = orderRepository.save(order);
        orderProductService.createOrderProducts(order);
        cartService.clearCart();
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(); // TODO ::
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findAllByStatus(status);
    }

    @Override
//    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrder(id);
        List<OrderProduct> orderProducts = order.getOrderProducts();
        orderProducts.forEach(orderProductService::deleteOrderProduct);
        orderRepository.delete(order);
    }
}
