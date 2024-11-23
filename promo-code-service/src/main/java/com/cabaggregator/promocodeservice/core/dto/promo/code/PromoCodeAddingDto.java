package com.cabaggregator.promocodeservice.core.dto.promo.code;

import java.time.LocalDateTime;

public record PromoCodeAddingDto(
        String value,
        Integer discountPercentage,
        LocalDateTime endDate,
        Long limits
) {
}
