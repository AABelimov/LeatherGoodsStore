package ru.aabelimov.leathergoodsstore.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aabelimov.leathergoodsstore.entity.Image;
import ru.aabelimov.leathergoodsstore.service.ImageService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("{id}")
    public void getImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Image image = imageService.getImage(id);
        Path path = Path.of(image.getImagePath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream()
        ) {
            response.setStatus(200);
            response.setContentType(image.getMediaType());
            response.setContentLength((int) image.getSize());
            is.transferTo(os);
        }
    }
}
