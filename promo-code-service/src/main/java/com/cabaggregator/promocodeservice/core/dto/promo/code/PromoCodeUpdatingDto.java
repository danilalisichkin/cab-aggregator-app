package com.cabaggregator.promocodeservice.core.dto.promo.code;

import java.time.LocalDateTime;

public record PromoCodeUpdatingDto(
        Integer discountPercentage,
        LocalDateTime endDate,
        Long limits
) {
}
