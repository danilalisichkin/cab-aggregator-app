package com.cabaggregator.driverservice.core.dto.car.details;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CarDetailsSettingDto(
        @NotNull
        LocalDate releaseDate,

        @NotNull
        @Min(1)
        @Max(8)
        Integer seatCapacity
) {
}
