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

    List<LeatherColor> getVisibleLeatherColorsByLeatherId(Long leatherId);

    List<LeatherColor> getLeatherColorsByColorId(Long colorId);

    List<Leather> getAllLeathersWithVisibleLeatherColors();

    List<LeatherColor> getAllLeatherColorsByLeatherId(Long leatherId);

    void updateLeatherColor(Long id, MultipartFile image) throws IOException;

    LeatherColor changeVisibility(Long id);

    void deleteLeatherColor(LeatherColor leatherColor);
}
