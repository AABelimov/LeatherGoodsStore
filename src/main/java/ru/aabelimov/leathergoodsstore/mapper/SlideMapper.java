package ru.aabelimov.leathergoodsstore.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateSlideDto;
import ru.aabelimov.leathergoodsstore.entity.Slide;

@Component
public class SlideMapper {

    public Slide toEntity(CreateOrUpdateSlideDto dto) {
        Slide slide = new Slide();
        slide.setTitle(dto.title());
        slide.setDescription(dto.description());
        return slide;
    }
}
