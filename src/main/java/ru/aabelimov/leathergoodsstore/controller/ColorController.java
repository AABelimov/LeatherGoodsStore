package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateColorDto;
import ru.aabelimov.leathergoodsstore.service.CategoryService;
import ru.aabelimov.leathergoodsstore.service.ColorService;

@Controller
@RequestMapping("colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;
    private final CategoryService categoryService;

    @PostMapping
    public String createColor(CreateOrUpdateColorDto dto,
                              @RequestParam(required = false) Long leatherId) {
        colorService.createColor(dto);
        if (leatherId == null) {
            return "redirect:/colors/settings";
        }
        return "redirect:/leathers-colors/leather/%d".formatted(leatherId);
    }

    @GetMapping("settings")
    public String getColorSettingsPage(Model model) {
        model.addAttribute("colors", colorService.getAllColors());
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "color/color-settings";
    }

    @GetMapping("{id}/edit")
    public String getColorEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("color", colorService.getColor(id));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "color/color-edit";
    }

    @PatchMapping("{id}")
    public String updateColor(@PathVariable Long id,
                              CreateOrUpdateColorDto dto) {
        colorService.updateColor(id, dto);
        return "redirect:/colors/settings";
    }

    @DeleteMapping("{id}")
    public String deleteColor(@PathVariable Long id) {
        colorService.deleteColor(id);
        return "redirect:/colors/settings";
    }
}
