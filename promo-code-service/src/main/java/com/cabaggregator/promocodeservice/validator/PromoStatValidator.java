package com.cabaggregator.promocodeservice.validator;

import com.cabaggregator.promocodeservice.core.constant.ApplicationMessages;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.exception.BadRequestException;
import com.cabaggregator.promocodeservice.repository.PromoStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PromoStatValidator {
    private final PromoStatRepository promoStatRepository;

    public void validatePromoCodeApplication(PromoCode code, UUID userId) {
        if (promoStatRepository.existsByPromoCodeAndUserId(code, userId)) {
            throw new BadRequestException(
                    ApplicationMessages.PROMO_CODE_ALREADY_APPLIED);
        }
    }
}
