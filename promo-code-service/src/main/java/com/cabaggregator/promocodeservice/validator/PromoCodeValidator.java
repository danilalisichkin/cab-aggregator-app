package com.cabaggregator.promocodeservice.validator;

import com.cabaggregator.promocodeservice.core.constant.ApplicationMessages;
import com.cabaggregator.promocodeservice.exception.BadRequestException;
import com.cabaggregator.promocodeservice.exception.DataUniquenessConflictException;
import com.cabaggregator.promocodeservice.exception.ValidationErrorException;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PromoCodeValidator {
    private final PromoCodeRepository promoCodeRepository;

    public void validatePromoCodeUniqueness(String promoCode) {
        if (promoCodeRepository.existsById(promoCode)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.PROMO_CODE_ALREADY_EXISTS,
                    promoCode);
        }
    }

    public void validateEndDate(LocalDate startDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new ValidationErrorException(
                    ApplicationMessages.PROMO_CODE_END_DATE_IN_PAST);
        }
    }

    public void validatePromoCodeExpiration(LocalDate endTime) {
        if (endTime.isBefore(LocalDate.now())) {
            throw new BadRequestException(
                    ApplicationMessages.PROMO_CODE_EXPIRED);
        }
    }
}
