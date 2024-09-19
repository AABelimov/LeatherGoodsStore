package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePromoCodeDto;
import ru.aabelimov.leathergoodsstore.entity.PromoCode;
import ru.aabelimov.leathergoodsstore.mapper.PromoCodeMapper;
import ru.aabelimov.leathergoodsstore.repository.PromoCodeRepository;
import ru.aabelimov.leathergoodsstore.service.PromoCodeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceDefaultImpl implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeMapper promoCodeMapper;

    @Override
    public void createPromoCode(CreateOrUpdatePromoCodeDto dto) {
        PromoCode promoCode = promoCodeMapper.toEntity(dto);
        promoCodeRepository.save(promoCode);
    }

    @Override
    public PromoCode getPromoCode(Long id) {
        return promoCodeRepository.findById(id).orElseThrow(); // TODO::
    }

    @Override
    public PromoCode getPromoCodeByCode(String code) {
        return promoCodeRepository.findByCode(code).orElse(null);
    }

    @Override
    public List<PromoCode> getAllPromoCodes() {
        return promoCodeRepository.findAll();
    }

    @Override
    public Boolean isExist(@RequestBody String promoCode) {
        return promoCodeRepository.existsByCode(promoCode);
    }

    @Override
    public void updatePromoCode(Long id, CreateOrUpdatePromoCodeDto dto) {
        PromoCode promoCode = getPromoCode(id);

        if (!dto.code().isBlank()) {
            promoCode.setCode(dto.code());
        }
        if (!dto.discountSize().isBlank()) {
            try {
                promoCode.setDiscountSize(Integer.parseInt(dto.discountSize()));
            } catch (NumberFormatException e) {
                e.printStackTrace(); // TODO ::
            }
        }
        promoCodeRepository.save(promoCode);
    }

    @Override
    public void deletePromoCode(Long id) {
        promoCodeRepository.delete(getPromoCode(id));
    }
}
