package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdateLeatherDto;
import ru.aabelimov.leathergoodsstore.entity.Cart;
import ru.aabelimov.leathergoodsstore.entity.Leather;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;
import ru.aabelimov.leathergoodsstore.entity.Role;
import ru.aabelimov.leathergoodsstore.service.CategoryService;
import ru.aabelimov.leathergoodsstore.service.LeatherColorService;
import ru.aabelimov.leathergoodsstore.service.LeatherService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("leathers")
@RequiredArgsConstructor
public class LeatherController {

    private final LeatherService leatherService;
    private final LeatherColorService leatherColorService;
    private final CategoryService categoryService;
    private final Cart cart;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createLeather(CreateOrUpdateLeatherDto dto,
                                @RequestParam MultipartFile image1,
                                @RequestParam MultipartFile image2) throws IOException {
        leatherService.createLeather(dto, image1, image2);
        return "redirect:/leathers/settings";
    }

    @GetMapping("{id}")
    public String getLeather(@PathVariable Long id, Model model, Authentication authentication) {
        Leather leather = leatherService.getLeather(id);
        boolean isAdmin = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()));

        if (!isAdmin && !leather.getIsVisible()) {
            throw new RuntimeException(); // TODO :: add exception
        }
        model.addAttribute("leather", leather);
        model.addAttribute("leatherColors", leatherColorService.getVisibleLeatherColorsByLeatherId(id));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        model.addAttribute("cart", cart);
        return "leather/leather-card";
    }

    @GetMapping
    public String getLeathers(Model model) {
        model.addAttribute("leathers", leatherService.getAllVisibleLeathers());
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        model.addAttribute("cart", cart);
        return "leather/leathers";
    }

    @GetMapping("settings")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getLeathersSettingsPage(Model model) {
        model.addAttribute("leathers", leatherService.getAllLeathers());
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "leather/leathers-settings";
    }

    @GetMapping("{id}/edit")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getLeatherEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("leather", leatherService.getLeather(id));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "leather/leather-edit";
    }

    @GetMapping("{id}/leather-colors")
    @ResponseBody
    public ResponseEntity<List<LeatherColor>> getLeatherColorsByLeatherId(@PathVariable Long id) {
        return ResponseEntity.ok(leatherColorService.getVisibleLeatherColorsByLeatherId(id));
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateLeather(@PathVariable Long id,
                                CreateOrUpdateLeatherDto dto,
                                @RequestParam MultipartFile image1,
                                @RequestParam MultipartFile image2) throws IOException {
        leatherService.updateLeather(id, dto, image1, image2);
        return "redirect:/leathers/settings";
    }

    @PatchMapping("{id}/change-visibility")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String changeVisibility(@PathVariable Long id) {
        leatherService.changeVisibility(id);
        return "redirect:/leathers/settings";
    }
}
