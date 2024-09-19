package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.aabelimov.leathergoodsstore.service.CategoryService;

@Controller
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public String createCategory(String category) {
        categoryService.createCategory(category);
        return "redirect:/products/settings";
    }

    @PatchMapping("{id}/update")
    public String updateCategory(@PathVariable Long id, String category) {
        categoryService.updateCategory(id, category);
        return "redirect:/products/settings";
    }

    @PatchMapping("{id}/hide")
    public String changeVisibility(@PathVariable Long id) {
        categoryService.changeVisibility(id);
        return "redirect:/products/settings";
    }
}
