package com.cabaggregator.rideservice.core.dto.promo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PromoCodeAddingDto(
        @NotNull
        @Size(min = 2, max = 50)
        String value,

        @NotNull
        @Positive
        @Max(value = 100)
        Integer discount,

        @NotNull
        LocalDateTime startDate,

        @NotNull
        LocalDateTime endDate
) {
}
