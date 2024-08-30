package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateProductLeatherColorDto;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.entity.ProductLeatherColor;
import ru.aabelimov.leathergoodsstore.service.*;

import java.io.IOException;

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
        productLeatherColorService.createProductLeatherColor(product, leatherColor, image);
        return "redirect:/products-leather-colors/product/%d".formatted(dto.productId());
    }

    @GetMapping("product/{productId}")
    public String getProductLeathersColorsPage(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.getProduct(productId));
        model.addAttribute("leathers", leatherService.getAllLeathers());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product-leather-color/product-leathers-colors";
    }

    // TODO ::
    @PatchMapping("{id}/delete-image")
    @ResponseBody
    public String deleteImage(@PathVariable Long id) throws IOException {
        ProductLeatherColor plc = productLeatherColorService.getProductLeatherColor(id);
        productLeatherColorService.deleteImage(plc);
        return id.toString();
    }
}
