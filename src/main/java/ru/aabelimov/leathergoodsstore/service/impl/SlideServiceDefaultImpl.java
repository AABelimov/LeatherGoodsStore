package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateSlideDto;
import ru.aabelimov.leathergoodsstore.entity.Slide;
import ru.aabelimov.leathergoodsstore.mapper.SlideMapper;
import ru.aabelimov.leathergoodsstore.repository.SlideRepository;
import ru.aabelimov.leathergoodsstore.service.ImageService;
import ru.aabelimov.leathergoodsstore.service.SlideService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlideServiceDefaultImpl implements SlideService {

    private final SlideRepository slideRepository;
    private final SlideMapper slideMapper;
    private final ImageService imageService;

    @Value("${path.to.images.for.slides}")
    private String imageDir;

    @Override
    @Transactional
    public void createSlide(CreateOrUpdateSlideDto dto, MultipartFile image) throws IOException {
        Slide slide = slideMapper.toEntity(dto);
        slide.setImage(imageService.createImage(image, imageDir));
        slideRepository.save(slide);
    }

    @Override
    public Slide getSlide(Long id) {
        return slideRepository.findById(id).orElseThrow(); // TODO :: add exception
    }

    @Override
    public List<Slide> getAllSlides() {
        return slideRepository.findAll();
    }

    @Override
    @Transactional
    public void updateSlide(Long id, CreateOrUpdateSlideDto dto, MultipartFile image) throws IOException {
        Slide slide = getSlide(id);

        if (!dto.title().isBlank()) {
            slide.setTitle(dto.title());
        }
        if (!dto.description().isBlank()) {
            slide.setDescription(dto.description());
        }
        if (!image.isEmpty()) {
            slide.setImage(imageService.updateImage(slide.getImage(), image, imageDir));
        }
        slideRepository.save(slide);
    }

    @Override
    @Transactional
    public void deleteSlide(Long id) throws IOException {
        Slide slide = getSlide(id);
        slideRepository.delete(slide);
        imageService.deleteImage(slide.getImage());
    }
}
