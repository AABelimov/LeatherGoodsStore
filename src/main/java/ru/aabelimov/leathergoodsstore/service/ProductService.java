package ru.aabelimov.leathergoodsstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateProductDto;
import ru.aabelimov.leathergoodsstore.entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void createProduct(CreateOrUpdateProductDto dto, MultipartFile image1, MultipartFile image2) throws IOException;

    Product getProduct(Long id);

    List<Product> getAllProducts();

    List<Product> getAllVisibleProducts();

    List<Product> getProductsByCategoryId(Long categoryId);

    void updateProduct(Long id, CreateOrUpdateProductDto dto, MultipartFile image1, MultipartFile image2) throws IOException;

    void changeVisibility(Long id);

//    void deleteProduct(Long id) throws IOException;
}
