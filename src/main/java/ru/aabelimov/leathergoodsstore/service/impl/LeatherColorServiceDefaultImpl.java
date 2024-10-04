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
import ru.aabelimov.leathergoodsstore.entity.ProductLeatherColor;
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
        leatherColor.setIsVisible(false);
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
    public List<LeatherColor> getAllLeatherColors() {
        return leatherColorRepository.findAllOrderById();
    }

    @Override
    public List<LeatherColor> getLeatherColorsByLeatherId(Long leatherId) {
        return leatherColorRepository.findAllByLeatherIdOrderById(leatherId);
    }

    @Override
    public List<LeatherColor> getVisibleLeatherColorsByLeatherId(Long leatherId) {
        return leatherColorRepository.findAllByLeatherIdAndIsVisibleOrderById(leatherId, true);
    }

    @Override
    public List<LeatherColor> getLeatherColorsByColorId(Long colorId) {
        return leatherColorRepository.findAllByColorIdOrderById(colorId);
    }

    @Override
    public List<Leather> getAllLeathersWithVisibleLeatherColors() {
        return leatherColorRepository.findAllLeathersWhereLeatherColorIsVisible(true);
    }

    @Override
    public void updateLeatherColor(Long id, MultipartFile image) throws IOException {
        LeatherColor leatherColor = getLeatherColor(id);
        imageService.updateImage(leatherColor.getImage(), image, imageDir);
    }

    @Override
    public LeatherColor changeVisibility(Long id) {
        LeatherColor leatherColor = getLeatherColor(id);
        leatherColor.setIsVisible(!leatherColor.getIsVisible());
        return leatherColorRepository.save(leatherColor);
    }

    @Override
    public void deleteLeatherColor(LeatherColor leatherColor) {
        List<ProductLeatherColor> productLeatherColors = productLeatherColorService.getAllByLeatherColorId(leatherColor.getId());

        if (productLeatherColors.isEmpty()) {
            leatherColorRepository.delete(leatherColor);
        }
    }
}
