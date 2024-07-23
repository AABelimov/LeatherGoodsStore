package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateProductDto;
import ru.aabelimov.leathergoodsstore.service.CategoryService;
import ru.aabelimov.leathergoodsstore.service.LeatherService;
import ru.aabelimov.leathergoodsstore.service.ProductService;

import java.io.IOException;

@Controller
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final LeatherService leatherService;
    private final CategoryService categoryService;

    @PostMapping
    public String createProduct(CreateOrUpdateProductDto dto,
                                @RequestParam MultipartFile image1,
                                @RequestParam MultipartFile image2) throws IOException {
        productService.createProduct(dto, image1, image2);
        return "redirect:/products/settings";
    }

    @GetMapping("{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("leathers", leatherService.getAllLeathers());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/product";
    }

    @GetMapping
    public String getProducts(@RequestParam(required = false) Long category, Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        if (category == null) {
            model.addAttribute("products", productService.getAllProducts());
        } else {
            model.addAttribute("products", productService.getProductsByCategoryId(category));
        }
        return "product/catalog";
    }

    @GetMapping("settings")
    public String getProductsSettingsPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/products-settings";
    }

    @GetMapping("{id}/edit")
    public String getProductEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/product-edit";
    }

    @PatchMapping("{id}")
    public String updateLeather(@PathVariable Long id,
                                CreateOrUpdateProductDto dto,
                                @RequestParam MultipartFile image1,
                                @RequestParam MultipartFile image2) throws IOException {
        productService.updateProduct(id, dto, image1, image2);
        return "redirect:/products/settings";
    }

    @DeleteMapping("{id}")
    public String deleteProduct(@PathVariable Long id) throws IOException {
        productService.deleteProduct(id);
        return "redirect:/products/settings";
    }
}
