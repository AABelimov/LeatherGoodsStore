package ru.aabelimov.leathergoodsstore.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePromoCodeDto;
import ru.aabelimov.leathergoodsstore.entity.PromoCode;

@Component
public class PromoCodeMapper {

    public PromoCode toEntity(CreateOrUpdatePromoCodeDto dto) {
        PromoCode promoCode = new PromoCode();
        promoCode.setCode(dto.code());
        try {
            promoCode.setDiscountSize(Integer.parseInt(dto.discountSize()));
        } catch (NumberFormatException e) {
            System.out.println("exception"); // TODO :: handle
        }
        return promoCode;
    }
}
