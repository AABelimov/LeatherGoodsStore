package ru.aabelimov.leathergoodsstore.service;

import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePromoCodeDto;
import ru.aabelimov.leathergoodsstore.entity.PromoCode;

import java.util.List;

public interface PromoCodeService {

    void createPromoCode(CreateOrUpdatePromoCodeDto dto);

    PromoCode getPromoCode(Long id);

    PromoCode getPromoCodeByCode(String code);

    List<PromoCode> getAllPromoCodes();

    Boolean isExist(String promoCode);

    void updatePromoCode(Long id, CreateOrUpdatePromoCodeDto dto);

    void deletePromoCode(Long id);
}
