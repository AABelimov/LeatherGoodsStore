package ru.aabelimov.leathergoodsstore.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateLeatherDto;
import ru.aabelimov.leathergoodsstore.entity.Leather;

import java.util.ArrayList;

@Component
public class LeatherMapper {

    public Leather toEntity(CreateOrUpdateLeatherDto dto) {
        Leather leather = new Leather();
        leather.setName(dto.name());
        leather.setDescription(dto.description());
        leather.setImages(new ArrayList<>());
        return leather;
    }
}
