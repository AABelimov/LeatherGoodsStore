package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateProductDto;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.mapper.ProductMapper;
import ru.aabelimov.leathergoodsstore.repository.ProductRepository;
import ru.aabelimov.leathergoodsstore.service.CategoryService;
import ru.aabelimov.leathergoodsstore.service.ImageService;
import ru.aabelimov.leathergoodsstore.service.ProductService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceDefaultImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ImageService imageService;
    private final CategoryService categoryService;

    @Value("${path.to.images.for.products}")
    private String imageDir;

    @Override
    @Transactional
    public void createProduct(CreateOrUpdateProductDto dto, MultipartFile image1, MultipartFile image2) throws IOException {
        Product product = productMapper.toEntity(dto);
        product.setCategory(categoryService.getCategory(dto.categoryId()));
        product.getImages().add(imageService.createImage(image1, imageDir));
        product.getImages().add(imageService.createImage(image2, imageDir));
        productRepository.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(); // TODO
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllVisibleProducts() {
        return productRepository.findAllByIsVisible(true);
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional
    public void updateProduct(Long id, CreateOrUpdateProductDto dto, MultipartFile image1, MultipartFile image2) throws IOException {
        Product product = getProduct(id);
        List<Image> images = product.getImages();

        if (!dto.name().isBlank()) {
            product.setName(dto.name());
        }
        if (!dto.price().isBlank()) {
            try {
                product.setPrice(Integer.parseInt(dto.price()));
            } catch (NumberFormatException e) {
                System.out.println("exception"); // TODO :: handle
            }
        }
        if (dto.categoryId() != null) {
            product.setCategory(categoryService.getCategory(dto.categoryId()));
        }
        if (!dto.description().isBlank()) {
            product.setDescription(dto.description());
        }
        if (!image1.isEmpty()) {
            imageService.updateImage(images.get(0), image1, imageDir);
        }
        if (!image2.isEmpty()) {
            imageService.updateImage(images.get(1), image2, imageDir);
        }
        productRepository.save(product);
    }

    @Override
    public void changeVisibility(Long id) {
        Product product = getProduct(id);
        product.setIsVisible(!product.getIsVisible());
        productRepository.save(product);
    }

//    @Override
//    @Transactional
//    public void deleteProduct(Long id) throws IOException {
//        Product product = getProduct(id);
//        List<Image> images = product.getImages();
//        productRepository.delete(product);
//        imageService.deleteImage(images.get(0));
//        imageService.deleteImage(images.get(1));
//    }
}
