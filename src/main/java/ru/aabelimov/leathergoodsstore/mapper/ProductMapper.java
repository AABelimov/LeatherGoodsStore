package ru.aabelimov.leathergoodsstore.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateProductDto;
import ru.aabelimov.leathergoodsstore.entity.Product;

import java.util.ArrayList;

@Component
public class ProductMapper {

    public Product toEntity(CreateOrUpdateProductDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setImages(new ArrayList<>());
        product.setIsVisible(false);
        try {
            product.setPrice(Integer.parseInt(dto.price()));
        } catch (NumberFormatException e) {
            System.out.println("exception"); // TODO :: handle
        }
        return product;
    }
}
