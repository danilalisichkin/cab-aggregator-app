package com.cabaggregator.promocodeservice.core.dto.promo.code;

import java.time.LocalDate;

public record PromoCodeDto(
        String value,
        Integer discountPercentage,
        LocalDate endDate,
        Long limits
) {
}
