package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateLeatherDto;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.entity.Leather;
import ru.aabelimov.leathergoodsstore.mapper.LeatherMapper;
import ru.aabelimov.leathergoodsstore.repository.LeatherRepository;
import ru.aabelimov.leathergoodsstore.service.ImageService;
import ru.aabelimov.leathergoodsstore.service.LeatherColorService;
import ru.aabelimov.leathergoodsstore.service.LeatherService;
import ru.aabelimov.leathergoodsstore.service.ProductLeatherColorService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeatherServiceDefaultImpl implements LeatherService {

    private final LeatherRepository leatherRepository;
    private final LeatherMapper leatherMapper;
    private final ImageService imageService;
    private final LeatherColorService leatherColorService;

    @Value("${path.to.images.for.leathers}")
    private String imageDir;

    @Override
    @Transactional
    public void createLeather(CreateOrUpdateLeatherDto dto, MultipartFile image1, MultipartFile image2) throws IOException {
        Leather leather = leatherMapper.toEntity(dto);
        leather.getImages().add(imageService.createImage(image1, imageDir));
        leather.getImages().add(imageService.createImage(image2, imageDir));
        leatherRepository.save(leather);
    }

    @Override
    public Leather getLeather(Long id) {
        return leatherRepository.findById(id).orElseThrow(); // TODO :: exception
    }

    @Override
    public List<Leather> getAllLeathers() {
        return leatherRepository.findAll();
    }

    @Override
    @Transactional
    public void updateLeather(Long id, CreateOrUpdateLeatherDto dto, MultipartFile image1, MultipartFile image2) throws IOException {
        Leather leather = getLeather(id);
        List<Image> images = leather.getImages();

        if (!dto.name().isBlank()) {
            leather.setName(dto.name());
        }
        if (!dto.description().isBlank()) {
            leather.setDescription(dto.description());
        }
        if (!image1.isEmpty()) {
            imageService.updateImage(images.get(0), image1, imageDir);
        }
        if (!image2.isEmpty()) {
            imageService.updateImage(images.get(1), image2, imageDir);
        }
        leatherRepository.save(leather);
    }

    @Override
//    @Transactional
    public void deleteLeather(Long id) throws IOException {
        Leather leather = getLeather(id);
        List<Image> images = leather.getImages();
        leatherColorService.deleteLeathersColorsByLeatherId(id);
        leatherRepository.delete(leather);
        imageService.deleteImage(images.get(0));
        imageService.deleteImage(images.get(1));
    }
}
