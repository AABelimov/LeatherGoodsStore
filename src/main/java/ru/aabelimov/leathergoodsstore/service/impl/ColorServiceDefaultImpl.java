package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateColorDto;
import ru.aabelimov.leathergoodsstore.entity.Color;
import ru.aabelimov.leathergoodsstore.repository.ColorRepository;
import ru.aabelimov.leathergoodsstore.service.ColorService;
import ru.aabelimov.leathergoodsstore.service.LeatherColorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceDefaultImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final LeatherColorService leatherColorService;

    @Override
    public void createColor(CreateOrUpdateColorDto dto) {
        Color color = new Color();
        color.setName(dto.colorName());
        color.setCode(dto.colorCode());
        colorRepository.save(color);
    }

    @Override
    public Color getColor(Long id) {
        return colorRepository.findById(id).orElseThrow(); // TODO :: exception
    }

    @Override
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    @Override
    public List<Color> getColorsByName(String colorName) {
        return colorRepository.getColorsByName(colorName);
    }

    @Override
    public void updateColor(Long id, CreateOrUpdateColorDto dto) {
        Color color = getColor(id);
        if (!dto.colorName().isBlank()) {
            color.setName(dto.colorName());
        }
        if (!dto.colorCode().isBlank()) {
            color.setCode(dto.colorCode());
        }
        colorRepository.save(color);
    }

    @Override
    @Transactional
    public void deleteColor(Long id) {
        Color color = getColor(id);
        leatherColorService.deleteLeathersColorsByColorId(id);
        colorRepository.delete(color);
    }
}
