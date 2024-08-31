package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.entity.Color;
import ru.aabelimov.leathergoodsstore.entity.Leather;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.repository.LeatherColorRepository;
import ru.aabelimov.leathergoodsstore.service.ImageService;
import ru.aabelimov.leathergoodsstore.service.LeatherColorService;
import ru.aabelimov.leathergoodsstore.service.ProductLeatherColorService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeatherColorServiceDefaultImpl implements LeatherColorService {

    private final LeatherColorRepository leatherColorRepository;
    private final ImageService imageService;
    private final ProductLeatherColorService productLeatherColorService;

    @Value("${path.to.images.for.leathers-colors}")
    private String imageDir;

    @Override
    @Transactional
    public void createLeatherColor(Leather leather, Color color, MultipartFile image) throws IOException {
        LeatherColor leatherColor = new LeatherColor();
        leatherColor.setLeather(leather);
        leatherColor.setColor(color);
        leatherColor.setImage(imageService.createImage(image, imageDir));
        try {
            leatherColorRepository.save(leatherColor);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage()); // TODO :: handle exception
        }
    }

    @Override
    public LeatherColor getLeatherColor(Long id) {
        return leatherColorRepository.findById(id).orElseThrow(); // TODO :: exception
    }

    @Override
    public List<LeatherColor> getLeatherColorsByLeatherId(Long leatherId) {
        return leatherColorRepository.findAllByLeatherId(leatherId);
    }

    @Override
    public void updateLeatherColor(Long id, MultipartFile image) throws IOException {
        LeatherColor leatherColor = getLeatherColor(id);
        imageService.updateImage(leatherColor.getImage(), image, imageDir);
    }

    @Override
    @Transactional
    public void deleteLeathersColorsByLeatherId(Long leatherId) {
        List<LeatherColor> leatherColors = leatherColorRepository.findAllByLeatherId(leatherId);
        deleteLeathersColors(leatherColors);
    }

    @Override
    @Transactional
    public void deleteLeathersColorsByColorId(Long colorId) {
        List<LeatherColor> leatherColors = leatherColorRepository.findAllByColorId(colorId);
        deleteLeathersColors(leatherColors);
    }

    @Transactional
    protected void deleteLeathersColors(List<LeatherColor> leatherColors) {
        leatherColors.forEach(leatherColor -> {
            try {
                deleteLeatherColor(leatherColor);
            } catch (IOException e) {
                throw new RuntimeException(e); // TODO :: handle
            }
        });
    }

    @Override
    @Transactional
    public void deleteLeatherColor(LeatherColor leatherColor) throws IOException {
        productLeatherColorService.deleteProductLeatherColorsByLeatherColorId(leatherColor.getId());
        leatherColorRepository.delete(leatherColor);
        imageService.deleteImage(leatherColor.getImage());
    }
}
