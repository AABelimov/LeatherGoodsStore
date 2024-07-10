package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.repository.ImageRepository;
import ru.aabelimov.leathergoodsstore.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceDefaultImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image createImage(MultipartFile file, String imageDir) throws IOException {
        Image image = new Image();
        return saveImage(image, file, imageDir);
    }

    private Image saveImage(Image image, MultipartFile file, String imageDir) throws IOException {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = null;
        Path imagePath = null;

        do {
            filename = UUID.randomUUID().toString();
            imagePath = Path.of(imageDir, filename + "." + extension);
        } while (Files.exists(imagePath));

        Files.createDirectories(imagePath.getParent());

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(imagePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            image.setImagePath(imagePath.toString());
            image.setSize(file.getSize());
            image.setMediaType(file.getContentType());
            return imageRepository.save(image);
        }
    }

    @Override
    public Image getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(); // TODO :: exception
    }

    @Override
    public Image updateImage(Image image, MultipartFile file, String imageDir) throws IOException {
        Files.deleteIfExists(Path.of(image.getImagePath()));
        return saveImage(image, file, imageDir);
    }

    @Override
    public void deleteImage(Image image) throws IOException {
        imageRepository.delete(image);
        Files.deleteIfExists(Path.of(image.getImagePath()));
    }
}
