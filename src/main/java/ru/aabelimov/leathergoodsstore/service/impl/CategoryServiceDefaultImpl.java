package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aabelimov.leathergoodsstore.entity.Category;
import ru.aabelimov.leathergoodsstore.repository.CategoryRepository;
import ru.aabelimov.leathergoodsstore.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceDefaultImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        category.setIsVisible(true);
        categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(); // TODO :: exception
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAllOrderById();
    }

    @Override
    public List<Category> getAllVisibleCategories() {
        return categoryRepository.findAllByIsVisibleOrderById(true);
    }

    @Override
    public void updateCategory(Long id, String categoryName) {
        Category category = getCategory(id);
        category.setName(categoryName);
        categoryRepository.save(category);
    }

    @Override
    public void changeVisibility(Long id) {
        Category category = getCategory(id);
        category.setIsVisible(!category.getIsVisible());
        categoryRepository.save(category);
    }
}
