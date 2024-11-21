package com.cabaggregator.rideservice.core.dto.ride.rate;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RideRateSettingDto(
        @NotNull
        @Min(value = 1, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
        @Max(value = 5, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
        Integer rate
) {
}
