package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.dto.UpdatedOrderProductQuantityDto;
import ru.aabelimov.leathergoodsstore.entity.*;
import ru.aabelimov.leathergoodsstore.mapper.OrderMapper;
import ru.aabelimov.leathergoodsstore.repository.OrderRepository;
import ru.aabelimov.leathergoodsstore.service.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceDefaultImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final OrderProductService orderProductService;
    private final PromoCodeService promoCodeService;
    private final CartService cartService;
    private final Cart cart;

    @Override
    @Transactional
    public Order createOrder(CreateOrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        User user = userService.getUserByUsername(dto.username());
        PromoCode promoCode = promoCodeService.getPromoCodeByCode(dto.promoCode());
        double discount = promoCode == null ? 0 : (cart.getTotalCost() * promoCode.getDiscountSize() / 100);

        if (user == null) {
            user = userService.createUser(dto);
        } else {
            userService.updateUser(user, dto);
        }
        order.setUser(user);
        order.setTotalQuantity(cart.getTotalQuantity());
        order.setTotalCost(cart.getTotalCost() - discount);
        order.setPromoCode(promoCode);
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
    @Transactional
    public UpdatedOrderProductQuantityDto updateOrderProductQuantity(Long orderProductId, String operator) {
        OrderProduct orderProduct = orderProductService.getOrderProduct(orderProductId);
        Order order = orderProduct.getOrder();
        Product product = orderProduct.getProductLeatherColor().getProduct();
        PromoCode promoCode = order.getPromoCode();
        double discount = promoCode == null ? 0 : (product.getPrice() * (double)promoCode.getDiscountSize() / 100);

        switch (operator) {
            case "plus" -> {
                orderProductService.updateQuantity(orderProduct, "plus");
                order.setTotalCost(order.getTotalCost() + (double)product.getPrice() - discount);
                order.setTotalQuantity(order.getTotalQuantity() + 1);
                orderRepository.save(order);
            }
            case "minus" -> {
                if (orderProduct.getQuantity() == 1) {
                    return new UpdatedOrderProductQuantityDto(order.getTotalCost(), orderProduct.getQuantity());
                } else {
                    orderProductService.updateQuantity(orderProduct, "minus");
                    order.setTotalCost(order.getTotalCost() - (double)product.getPrice() + discount);
                    order.setTotalQuantity(order.getTotalQuantity() - 1);
                    orderRepository.save(order);
                }
            }
        }
        return new UpdatedOrderProductQuantityDto(order.getTotalCost(), orderProduct.getQuantity());
    }

    @Override
    @Transactional
    public UpdatedOrderProductQuantityDto deleteOrderProduct(Long orderProductId) {
        OrderProduct orderProduct = orderProductService.getOrderProduct(orderProductId);
        Order order = orderProduct.getOrder();
        Product product = orderProduct.getProductLeatherColor().getProduct();
        PromoCode promoCode = order.getPromoCode();
        double discount = promoCode == null ? 0 : (double)(product.getPrice() * orderProduct.getQuantity() * promoCode.getDiscountSize() / 100);

        order.setTotalCost(order.getTotalCost() - product.getPrice() * orderProduct.getQuantity() + discount);
        order.setTotalQuantity(order.getTotalQuantity() - orderProduct.getQuantity());

        orderProductService.deleteOrderProduct(orderProduct);
        orderRepository.save(order);
        return new UpdatedOrderProductQuantityDto(order.getTotalCost(), orderProduct.getQuantity());
    }

    @Override
//    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrder(id);
        List<OrderProduct> orderProducts = orderProductService.getAllByOrderId(id);
        orderProducts.forEach(orderProductService::deleteOrderProduct);
        orderRepository.delete(order);
    }
}
