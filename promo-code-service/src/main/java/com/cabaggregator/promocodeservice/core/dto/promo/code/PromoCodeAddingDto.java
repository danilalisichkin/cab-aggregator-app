package com.cabaggregator.promocodeservice.core.dto.promo.code;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PromoCodeAddingDto(
        @NotEmpty
        @Size(min = 2, max = 20)
        String value,

        @NotNull
        @Min(1)
        @Max(100)
        Integer discountPercentage,

        @NotNull
        LocalDate endDate,

        @NotNull
        @Min(1)
        @Max(1000)
        Long limits
) {
}
