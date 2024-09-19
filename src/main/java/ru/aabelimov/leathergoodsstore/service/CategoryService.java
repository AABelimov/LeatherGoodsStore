package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.entity.Category;

import java.util.List;

public interface CategoryService {

    void createCategory(String categoryName);

    Category getCategory(Long id);

    List<Category> getAllCategories();

    List<Category> getAllVisibleCategories();

    void updateCategory(Long id, String categoryName);

    void changeVisibility(Long id);
}
