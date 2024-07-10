package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateSlideDto;
import ru.aabelimov.leathergoodsstore.service.SlideService;

import java.io.IOException;

@Controller
@RequestMapping("slides")
@RequiredArgsConstructor
public class SlideController {

    private final SlideService slideService;

    @PostMapping
    public String createSlide(CreateOrUpdateSlideDto dto,
                              @RequestParam MultipartFile image) throws IOException {
        slideService.createSlide(dto, image);
        return "redirect:/slides/settings";
    }

    @GetMapping("settings")
    public String getSlideSettingsPage(Model model) {
        model.addAttribute("slides", slideService.getAllSlides());
        return "slide/slider-settings";
    }

    @GetMapping("{id}/edit")
    public String getSlideEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("slide", slideService.getSlide(id));
        return "slide/slider-edit";
    }

    @PatchMapping("{id}")
    public String updateSlide(@PathVariable Long id,
                              CreateOrUpdateSlideDto dto,
                              @RequestParam MultipartFile image) throws IOException {
        slideService.updateSlide(id, dto, image);
        return "redirect:/slides/settings";
    }

    @DeleteMapping("{id}")
    public String deleteSlide(@PathVariable Long id) throws IOException {
        slideService.deleteSlide(id);
        return "redirect:/slides/settings";
    }
}
