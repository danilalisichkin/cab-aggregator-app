package com.cabaggregator.ratingservice.core.dto.passenger;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PassengerRateSettingDto(
        @NotNull
        @Min(1)
        @Max(5)
        Integer rate
) {
}
