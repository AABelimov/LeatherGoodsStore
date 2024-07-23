package ru.aabelimov.leathergoodsstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.entity.Image;

import java.io.IOException;

public interface ImageService {

    Image createImage(MultipartFile image, String imageDir) throws IOException;

    Image getImage(Long id);

    void updateImage(Image image, MultipartFile file, String imageDir) throws IOException;

    void deleteImage(Image image) throws IOException;
}
