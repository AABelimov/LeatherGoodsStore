package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateLeatherColorDto;
import ru.aabelimov.leathergoodsstore.entity.Color;
import ru.aabelimov.leathergoodsstore.entity.Leather;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.service.CategoryService;
import ru.aabelimov.leathergoodsstore.service.ColorService;
import ru.aabelimov.leathergoodsstore.service.LeatherColorService;
import ru.aabelimov.leathergoodsstore.service.LeatherService;

import java.io.IOException;

@Controller
@RequestMapping("leathers-colors")
@RequiredArgsConstructor
public class LeatherColorController {

    private final LeatherColorService leatherColorService;
    private final LeatherService leatherService;
    private final ColorService colorService;
    private final CategoryService categoryService;

    @PostMapping
    public String createLeatherColor(CreateLeatherColorDto dto,
                                     @RequestParam MultipartFile image) throws IOException {
        Leather leather = leatherService.getLeather(dto.leatherId());
        Color color = colorService.getColor(dto.colorId());
        leatherColorService.createLeatherColor(leather, color, image);
        return "redirect:/leathers-colors/leather/%d".formatted(dto.leatherId());
    }

    @GetMapping("leather/{leatherId}")
    public String getLeatherColorsPage(@PathVariable Long leatherId,
                                       @RequestParam(required = false) String colorName,
                                       Model model) {
        model.addAttribute("leather", leatherService.getLeather(leatherId));
        model.addAttribute("leatherColors", leatherColorService.getLeatherColorsByLeatherId(leatherId));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());

        if (colorName == null) {
            model.addAttribute("colors", colorService.getAllColors());
        } else {
            model.addAttribute("colors", colorService.getColorsByName(colorName));
        }
        return "leather-color/leather-colors";
    }

    @PatchMapping("{id}")
    public String updateLeatherColor(@PathVariable Long id,
                                     @RequestParam MultipartFile image) throws IOException {
        LeatherColor leatherColor = leatherColorService.getLeatherColor(id);
        leatherColorService.updateLeatherColor(id, image);
        return "redirect:/leathers-colors/leather/%d".formatted(leatherColor.getLeather().getId());
    }

    @DeleteMapping("{id}")
    public String deleteLeatherColor(@PathVariable Long id) throws IOException {
        LeatherColor leatherColor = leatherColorService.getLeatherColor(id);
        leatherColorService.deleteLeatherColor(leatherColor);
        return "redirect:/leathers-colors/leather/%d".formatted(leatherColor.getLeather().getId());
    }
}
