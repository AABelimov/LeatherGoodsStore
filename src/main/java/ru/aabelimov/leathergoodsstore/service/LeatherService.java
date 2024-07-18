package ru.aabelimov.leathergoodsstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateLeatherDto;
import ru.aabelimov.leathergoodsstore.entity.Leather;

import java.io.IOException;
import java.util.List;

public interface LeatherService {

    void createLeather(CreateOrUpdateLeatherDto dto, MultipartFile image1, MultipartFile image2) throws IOException;

    Leather getLeather(Long id);

    List<Leather> getAllLeathers();

    void updateLeather(Long id, CreateOrUpdateLeatherDto dto, MultipartFile image1, MultipartFile image2) throws IOException;

    void deleteLeather(Long id) throws IOException;

}
