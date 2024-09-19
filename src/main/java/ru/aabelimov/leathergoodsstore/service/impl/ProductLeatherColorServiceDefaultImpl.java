package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.entity.ProductLeatherColor;
import ru.aabelimov.leathergoodsstore.repository.ProductLeatherColorRepository;
import ru.aabelimov.leathergoodsstore.service.ImageService;
import ru.aabelimov.leathergoodsstore.service.ProductLeatherColorService;

import java.io.IOException;
import java.util.List;

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
        ProductLeatherColor productLeatherColor = getProductLeatherColorByProductAndLeatherColor(product, leatherColor);
        if (productLeatherColor == null) {
            ProductLeatherColor newProductLeatherColor = new ProductLeatherColor();
            newProductLeatherColor.setProduct(product);
            newProductLeatherColor.setLeatherColor(leatherColor);
            newProductLeatherColor.setImage(imageService.createImage(image, imageDir));
            productLeatherColorRepository.save(newProductLeatherColor);
        } else {
            if (productLeatherColor.getImage() == null) {
                productLeatherColor.setImage(imageService.createImage(image, imageDir));
                productLeatherColorRepository.save(productLeatherColor);
            } else {
                updateProductLeatherColor(productLeatherColor.getId(), image);
            }
        }
    }

    @Override
    public ProductLeatherColor createProductLeatherColor(Product product, LeatherColor leatherColor) {
        ProductLeatherColor productLeatherColor = new ProductLeatherColor();
        productLeatherColor.setProduct(product);
        productLeatherColor.setLeatherColor(leatherColor);
        productLeatherColor.setImage(null);
        return productLeatherColorRepository.save(productLeatherColor);
    }

    @Override
    public ProductLeatherColor getProductLeatherColor(Long id) {
        return productLeatherColorRepository.findById(id).orElseThrow(); // TODO :: exception
    }

    @Override
    public ProductLeatherColor getProductLeatherColorByProductAndLeatherColor(Product product, LeatherColor leatherColor) {
        return productLeatherColorRepository.findByProductAndLeatherColor(product, leatherColor).orElse(null);
    }

    @Override
    public List<ProductLeatherColor> getAllByProductId(Long productId) {
        return productLeatherColorRepository.findAllByProductId(productId);
    }

    @Override
    public void updateProductLeatherColor(Long id, MultipartFile image) throws IOException {
        ProductLeatherColor productLeatherColor = getProductLeatherColor(id);
        imageService.updateImage(productLeatherColor.getImage(), image, imageDir);
    }

    @Override
    public void deleteImage(ProductLeatherColor plc) throws IOException { // TODO :: exchange on try catch
        Image image = plc.getImage();
        plc.setImage(null);
        imageService.deleteImage(image);
    }

    @Override
//    @Transactional
    public void deleteProductLeatherColor(ProductLeatherColor plc) throws IOException {
        productLeatherColorRepository.delete(plc);
        if (plc.getImage() != null) {
            imageService.deleteImage(plc.getImage());
        }
    }

    @Override
//    @Transactional
    public void deleteProductLeatherColorsByLeatherColorId(Long leatherColorId) {
        List<ProductLeatherColor> productLeatherColors = productLeatherColorRepository.findAllByLeatherColorId(leatherColorId);
        productLeatherColors.forEach(plc -> {
            try {
                deleteProductLeatherColor(plc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
