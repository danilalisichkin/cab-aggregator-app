package com.cabaggregator.rideservice.core.dto.ride.rate;

import com.cabaggregator.rideservice.core.enums.UserRole;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RideRateSettingDto(
        @NotNull
        UserRole role,

        @NotNull
        @Min(1)
        @Max(5)
        Integer rate
) {
}
