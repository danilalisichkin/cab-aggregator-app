package com.cabaggregator.rideservice.core.dto.promo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PromoCodeUpdatingDto(
        @NotNull
        @Min(1)
        @Max(100)
        Integer discount,

        @NotNull
        LocalDateTime startDate,

        @NotNull
        LocalDateTime endDate
) {
}
