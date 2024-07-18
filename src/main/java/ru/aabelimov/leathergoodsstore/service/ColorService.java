package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateColorDto;
import ru.aabelimov.leathergoodsstore.entity.Color;

import java.util.List;

public interface ColorService {

    void createColor(CreateOrUpdateColorDto dto);

    Color getColor(Long id);

    List<Color> getAllColors();

    List<Color> getColorsByName(String colorName);

    void updateColor(Long id, CreateOrUpdateColorDto dto);

    void deleteColor(Long id);

}
