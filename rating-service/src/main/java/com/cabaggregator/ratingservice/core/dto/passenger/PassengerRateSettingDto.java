package com.cabaggregator.ratingservice.core.dto.passenger;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Entry to set passenger rate")
public record PassengerRateSettingDto(
        @NotNull
        @Min(1)
        @Max(5)
        Integer rate
) {
}
