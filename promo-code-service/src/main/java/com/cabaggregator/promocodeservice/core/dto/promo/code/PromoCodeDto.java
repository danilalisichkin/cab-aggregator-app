package com.cabaggregator.promocodeservice.core.dto.promo.code;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Server response with promo code data")
public record PromoCodeDto(
        String value,
        Integer discountPercentage,
        LocalDate endDate,
        Long limit
) {
}
