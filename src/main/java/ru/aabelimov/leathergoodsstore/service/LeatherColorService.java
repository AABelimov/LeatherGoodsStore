package ru.aabelimov.leathergoodsstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.entity.Color;
import ru.aabelimov.leathergoodsstore.entity.Leather;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;

import java.io.IOException;
import java.util.List;

public interface LeatherColorService {

    void createLeatherColor(Leather leather, Color color, MultipartFile image) throws IOException;

    LeatherColor getLeatherColor(Long id);

    List<LeatherColor> getAllLeatherColors();

    List<LeatherColor> getLeatherColorsByLeatherId(Long leatherId);

    void updateLeatherColor(Long id, MultipartFile image) throws IOException;

    void deleteLeathersColorsByLeatherId(Long leatherId);

    void deleteLeathersColorsByColorId(Long colorId);

    void deleteLeatherColor(LeatherColor leatherColor) throws IOException;
}
