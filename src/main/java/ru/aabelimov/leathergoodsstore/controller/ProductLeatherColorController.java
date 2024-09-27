package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateProductLeatherColorDto;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.entity.Leather;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.service.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("products-leather-colors")
@RequiredArgsConstructor
public class ProductLeatherColorController {

    private final ProductLeatherColorService productLeatherColorService;
    private final ProductService productService;
    private final LeatherColorService leatherColorService;
    private final LeatherService leatherService;
    private final CategoryService categoryService;

    @PostMapping
    public String createProductLeatherColor(CreateProductLeatherColorDto dto,
                                            @RequestParam MultipartFile image) throws IOException {
        Product product = productService.getProduct(dto.productId());
        LeatherColor leatherColor = leatherColorService.getLeatherColor(dto.leatherColorId());
        productLeatherColorService.addProductLeatherColorImage(product, leatherColor, image);
        return "redirect:/products-leather-colors/product/%d".formatted(dto.productId());
    }

    @GetMapping("product/{productId}")
    public String getProductLeathersColorsPage(@PathVariable Long productId, Model model) {
        List<Leather> leathers = leatherService.getAllLeathers();
        model.addAttribute("product", productService.getProduct(productId));
        model.addAttribute("leathers", leathers);
        model.addAttribute("leatherColors", leatherColorService.getLeatherColorsByLeatherId(leathers.get(0).getId()));
        model.addAttribute("productLeatherColors", productLeatherColorService.getAllByProductId(productId));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "product-leather-color/product-leathers-colors";
    }

    @PatchMapping("{id}/delete-image/{imageId}")
    @ResponseBody
    public ResponseEntity<Image> deleteImage(@PathVariable Long id, @PathVariable Long imageId) throws IOException {
        return ResponseEntity.ok(productLeatherColorService.deleteImage(id, imageId));
    }
}
