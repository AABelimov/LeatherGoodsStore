package ru.aabelimov.leathergoodsstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.entity.ProductLeatherColor;

import java.io.IOException;
import java.util.List;

public interface ProductLeatherColorService {

    ProductLeatherColor createProductLeatherColor(Product product, LeatherColor leatherColor);

    void addProductLeatherColorImage(Product product, LeatherColor leatherColor, MultipartFile image) throws IOException;

    ProductLeatherColor getProductLeatherColor(Long id);

    ProductLeatherColor getProductLeatherColorByProductAndLeatherColor(Product product, LeatherColor leatherColor);

    List<ProductLeatherColor> getAllByProductId(Long productId);

    List<ProductLeatherColor> getAllByLeatherColorId(Long leatherColorId);

    Image deleteImage(Long id, Long imageId) throws IOException;

    void deleteProductLeatherColor(ProductLeatherColor plc) throws IOException;

    void deleteProductLeatherColorsByLeatherColorId(Long leatherColorId);
}
