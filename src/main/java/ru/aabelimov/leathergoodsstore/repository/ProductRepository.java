package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryIdOrderById(Long categoryId);

    List<Product> findAllByIsVisibleOrderById(Boolean isVisible);

    @Query("SELECT p FROM Product p WHERE p.isVisible = true AND p.category.isVisible = true ORDER BY p.id ASC")
    List<Product> findAllVisibleWhereCategoryIsVisible();

    @Query("SELECT p FROM Product p ORDER BY p.id ASC")
    List<Product> findAllOrderById();
}
