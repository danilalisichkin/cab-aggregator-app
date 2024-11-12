package com.cabaggregator.rideservice.core.dto.promo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PromoCodeUpdatingDto(
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
