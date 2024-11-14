package com.cabaggregator.rideservice.core.dto.ride.rate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RideRateSettingDto(
        @NotNull
        @Min(1)
        @Max(5)
        Integer rate
) {
}
