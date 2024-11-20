package com.cabaggregator.driverservice.core.dto.car.details;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Entry to set details of existing car")
public record CarDetailsSettingDto(
        @NotNull
        LocalDate releaseDate,

        @NotNull
        @Min(value = 1, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
        @Max(value = 8, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
        Integer seatCapacity
) {
}
