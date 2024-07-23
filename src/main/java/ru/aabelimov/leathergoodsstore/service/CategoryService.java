package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.entity.Category;

import java.util.List;

public interface CategoryService {

    void createCategory(String categoryName);

    Category getCategory(Long id);

    List<Category> getAllCategories();
}
