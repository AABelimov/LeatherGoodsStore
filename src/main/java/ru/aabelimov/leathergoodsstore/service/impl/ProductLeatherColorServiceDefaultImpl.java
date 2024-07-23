package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.entity.ProductLeatherColor;
import ru.aabelimov.leathergoodsstore.repository.ProductLeatherColorRepository;
import ru.aabelimov.leathergoodsstore.service.ImageService;
import ru.aabelimov.leathergoodsstore.service.ProductLeatherColorService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductLeatherColorServiceDefaultImpl implements ProductLeatherColorService {

    private final ProductLeatherColorRepository productLeatherColorRepository;
    private final ImageService imageService;

    @Value("${path.to.images.for.products-leather-colors}")
    private String imageDir;

    @Override
    @Transactional
    public void createProductLeatherColor(Product product, LeatherColor leatherColor, MultipartFile image) throws IOException {
        ProductLeatherColor productLeatherColor = productLeatherColorRepository.findByProductAndLeatherColor(product, leatherColor);
        if (productLeatherColor == null) {
            ProductLeatherColor newProductLeatherColor = new ProductLeatherColor();
            newProductLeatherColor.setProduct(product);
            newProductLeatherColor.setLeatherColor(leatherColor);
            newProductLeatherColor.setImage(imageService.createImage(image, imageDir));
            productLeatherColorRepository.save(newProductLeatherColor);
        } else {
            updateProductLeatherColor(productLeatherColor.getId(), image);
        }
    }

    @Override
    public ProductLeatherColor getProductLeatherColor(Long id) {
        return productLeatherColorRepository.findById(id).orElseThrow(); // TODO :: exception
    }

    @Override
    public void updateProductLeatherColor(Long id, MultipartFile image) throws IOException {
        ProductLeatherColor productLeatherColor = getProductLeatherColor(id);
        imageService.updateImage(productLeatherColor.getImage(), image, imageDir);
    }
}
