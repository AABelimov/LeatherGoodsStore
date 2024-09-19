package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePromoCodeDto;
import ru.aabelimov.leathergoodsstore.service.CategoryService;
import ru.aabelimov.leathergoodsstore.service.PromoCodeService;

@Controller
@RequestMapping("promo-codes")
@RequiredArgsConstructor
public class PromoCodeController {

    private final PromoCodeService promoCodeService;
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createPromoCode(CreateOrUpdatePromoCodeDto dto) {
        promoCodeService.createPromoCode(dto);
        return "redirect:/promo-codes/settings";
    }

    @GetMapping("check")
    @ResponseBody
    public ResponseEntity<Boolean> checkPromoCode(@RequestParam String promoCode) {
        return ResponseEntity.ok(promoCodeService.isExist(promoCode));
    }

    @GetMapping("settings")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getPromoCodesSettingsPage(Model model) {
        model.addAttribute("promoCodes", promoCodeService.getAllPromoCodes());
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "promo-code/promo-codes-settings";
    }

    @GetMapping("{id}/edit")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getPromoCodeEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("promoCode", promoCodeService.getPromoCode(id));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "promo-code/promo-code-edit";
    }

    @PatchMapping("{id}")
    public String updatePromoCode(@PathVariable Long id,
                                  CreateOrUpdatePromoCodeDto dto) {
        promoCodeService.updatePromoCode(id, dto);
        return "redirect:/promo-codes/settings";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePromoCode(@PathVariable Long id) {
        promoCodeService.deletePromoCode(id);
        return "redirect:/promo-codes/settings";
    }
}
