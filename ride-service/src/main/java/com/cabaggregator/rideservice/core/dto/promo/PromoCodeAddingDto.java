package com.cabaggregator.rideservice.core.dto.promo;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PromoCodeAddingDto(
        @NotEmpty
        @Size(min = 2, max = 50, message = ValidationErrors.INVALID_STRING_LENGTH)
        String value,

        @NotEmpty
        @Min(value = 1, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
        @Max(value = 100, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
        Integer discount,

        @NotNull
        LocalDateTime startDate,

        @NotNull
        LocalDateTime endDate
) {
}
