package com.cabaggregator.promocodeservice.core.dto.promo.code;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PromoCodeUpdatingDto(
        @NotNull
        @Min(1)
        @Max(100)
        Integer discountPercentage,

        @NotNull
        LocalDate endDate,

        @NotNull
        @Min(1)
        @Max(1000)
        Long limit
) {
}
