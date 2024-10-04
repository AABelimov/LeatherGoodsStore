package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aabelimov.leathergoodsstore.entity.OrderProduct;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findAllByOrderIdOrderById(Long orderId);
}
