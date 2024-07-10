package ru.aabelimov.leathergoodsstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateSlideDto;
import ru.aabelimov.leathergoodsstore.entity.Slide;

import java.io.IOException;
import java.util.List;

public interface SlideService {

    void createSlide(CreateOrUpdateSlideDto dto, MultipartFile image) throws IOException;

    Slide getSlide(Long id);

    List<Slide> getAllSlides();

    void updateSlide(Long id, CreateOrUpdateSlideDto dto, MultipartFile image) throws IOException;

    void deleteSlide(Long id) throws IOException;

//    byte[] getImage(Long id) throws IOException;
}
