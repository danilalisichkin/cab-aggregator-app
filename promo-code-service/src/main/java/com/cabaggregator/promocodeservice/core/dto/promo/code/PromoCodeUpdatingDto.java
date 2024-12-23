package com.cabaggregator.promocodeservice.core.dto.promo.code;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Entry to update existing promo code")
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
