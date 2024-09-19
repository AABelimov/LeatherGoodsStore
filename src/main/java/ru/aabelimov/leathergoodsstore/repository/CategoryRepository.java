package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByIsVisibleOrderById(boolean isVisible);

    @Query("FROM Category c ORDER BY c.id ASC")
    List<Category> findAllOrderById();
}
