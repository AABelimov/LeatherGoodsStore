package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.entity.ProductLeatherColor;

import java.util.List;
import java.util.Optional;

public interface ProductLeatherColorRepository extends JpaRepository<ProductLeatherColor, Long> {

    Optional<ProductLeatherColor> findByProductAndLeatherColor(Product product, LeatherColor leatherColor);

    List<ProductLeatherColor> findAllByLeatherColorIdOrderById(Long leatherColorId);

    List<ProductLeatherColor> findAllByProductIdOrderById(Long productId);
}
