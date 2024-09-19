package ru.aabelimov.leathergoodsstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.entity.ProductLeatherColor;

import java.io.IOException;
import java.util.List;

public interface ProductLeatherColorService {

    void createProductLeatherColor(Product product, LeatherColor leatherColor, MultipartFile image) throws IOException;

    ProductLeatherColor createProductLeatherColor(Product product, LeatherColor leatherColor);

    ProductLeatherColor getProductLeatherColor(Long id);

    ProductLeatherColor getProductLeatherColorByProductAndLeatherColor(Product product, LeatherColor leatherColor);

    List<ProductLeatherColor> getAllByProductId(Long productId);

    void updateProductLeatherColor(Long id, MultipartFile image) throws IOException;

    void deleteImage(ProductLeatherColor plc) throws IOException;

    void deleteProductLeatherColor(ProductLeatherColor plc) throws IOException;

    void deleteProductLeatherColorsByLeatherColorId(Long leatherColorId);
}
