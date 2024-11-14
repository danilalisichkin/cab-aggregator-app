package com.cabaggregator.rideservice.validator;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.DataUniquenessConflictException;
import com.cabaggregator.rideservice.repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PromoCodeValidator {
    private final PromoCodeRepository promoCodeRepository;

    public void validatePromoCodeUniqueness(String promoCode) {
        if (promoCodeRepository.existsByValue(promoCode)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.PROMO_CODE_ALREADY_EXISTS,
                    promoCode);
        }
    }

    public void validateStartDate(LocalDateTime startDate) {
        if (startDate.isBefore(LocalDateTime.now())) {
            throw new BadRequestException(
                    ApplicationMessages.PROMO_CODE_START_DATE_IN_PAST);
        }
    }

    public void validateEndDate(LocalDateTime startDate) {
        if (startDate.isBefore(LocalDateTime.now())) {
            throw new BadRequestException(
                    ApplicationMessages.PROMO_CODE_END_DATE_IN_PAST);
        }
    }

    public void validateStartDateWithEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (!startDate.isBefore(endDate)) {
            throw new BadRequestException(
                    ApplicationMessages.PROMO_CODE_START_DATE_BEFORE_END_DATE);
        }
    }

    public void validatePromoCodeExpiration(LocalDateTime endTime) {
        if (endTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException(
                    ApplicationMessages.PROMO_CODE_EXPIRED);
        }
    }
}
