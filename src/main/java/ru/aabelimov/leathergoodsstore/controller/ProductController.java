package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateProductDto;
import ru.aabelimov.leathergoodsstore.entity.Cart;
import ru.aabelimov.leathergoodsstore.entity.Leather;
import ru.aabelimov.leathergoodsstore.service.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final LeatherService leatherService;
    private final ProductLeatherColorService productLeatherColorService;
    private final CategoryService categoryService;
    private final LeatherColorService leatherColorService;
    private final Cart cart;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createProduct(CreateOrUpdateProductDto dto,
                                @RequestParam MultipartFile image1,
                                @RequestParam MultipartFile image2) throws IOException {
        productService.createProduct(dto, image1, image2);
        return "redirect:/products/settings";
    }

    @GetMapping("{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        List<Leather> leathers = leatherService.getAllLeathers();
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("leathers", leathers);
        model.addAttribute("leatherColors", leatherColorService.getAllLeatherColorsByLeather(leathers.get(0)));
        model.addAttribute("productLeatherColors", productLeatherColorService.getAllByProductId(id));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        model.addAttribute("cart", cart);
        return "product/product";
    }

    @GetMapping
    public String getProducts(@RequestParam(required = false) Long category, Model model) {
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        model.addAttribute("cart", cart);
        model.addAttribute("leatherColors", leatherColorService.getAllLeatherColors());
        if (category == null) {
            model.addAttribute("products", productService.getAllProducts());
        } else {
            model.addAttribute("products", productService.getProductsByCategoryId(category));
        }
        return "product/catalog";
    }

    @GetMapping("settings")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getProductsSettingsPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("leatherColors", leatherColorService.getAllLeatherColors());
        return "product/products-settings";
    }

    @GetMapping("{id}/edit")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getProductEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "product/product-edit";
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateLeather(@PathVariable Long id,
                                CreateOrUpdateProductDto dto,
                                @RequestParam MultipartFile image1,
                                @RequestParam MultipartFile image2) throws IOException {
        productService.updateProduct(id, dto, image1, image2);
        return "redirect:/products/settings";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteProduct(@PathVariable Long id) throws IOException {
        productService.deleteProduct(id);
        return "redirect:/products/settings";
    }
}
