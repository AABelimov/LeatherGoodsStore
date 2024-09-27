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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductLeatherColorServiceDefaultImpl implements ProductLeatherColorService {

    private final ProductLeatherColorRepository productLeatherColorRepository;
    private final ImageService imageService;

    @Value("${path.to.images.for.products-leather-colors}")
    private String imageDir;

    @Override
    public ProductLeatherColor createProductLeatherColor(Product product, LeatherColor leatherColor) {
        ProductLeatherColor productLeatherColor = new ProductLeatherColor();
        productLeatherColor.setProduct(product);
        productLeatherColor.setLeatherColor(leatherColor);
        return productLeatherColorRepository.save(productLeatherColor);
    }

    @Override
    @Transactional
    public void addProductLeatherColorImage(Product product, LeatherColor leatherColor, MultipartFile image) throws IOException {
        ProductLeatherColor productLeatherColor = getProductLeatherColorByProductAndLeatherColor(product, leatherColor);
        if (productLeatherColor == null) {
            productLeatherColor = createProductLeatherColor(product, leatherColor);
        }
        if (productLeatherColor.getImages() == null) {
            productLeatherColor.setImages(new ArrayList<>());
        }
        productLeatherColor.getImages().add(imageService.createImage(image, imageDir));
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
    public Image deleteImage(Long id, Long imageId) throws IOException { // TODO :: exchange on try catch
        ProductLeatherColor plc = getProductLeatherColor(id);
        Image image = imageService.getImage(imageId);

        plc.getImages().remove(image);
        imageService.deleteImage(image);
        productLeatherColorRepository.save(plc);
        return image;
    }

    @Override
//    @Transactional
    public void deleteProductLeatherColor(ProductLeatherColor plc) throws IOException {
        productLeatherColorRepository.delete(plc);
//        if (plc.getImage() != null) {
//            imageService.deleteImage(plc.getImage());
//        }
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
