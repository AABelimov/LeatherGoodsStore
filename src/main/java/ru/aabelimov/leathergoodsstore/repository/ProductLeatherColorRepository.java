package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.entity.ProductLeatherColor;

public interface ProductLeatherColorRepository extends JpaRepository<ProductLeatherColor, Long> {
    ProductLeatherColor findByProductAndLeatherColor(Product product, LeatherColor leatherColor);
}
