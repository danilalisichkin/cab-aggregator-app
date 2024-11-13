package com.cabaggregator.driverservice.core.dto.car;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.constant.ValidationRegex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CarUpdatingDto(
        @NotNull
        @Pattern(regexp = ValidationRegex.LICENCE_PLATE_BELARUS_FORMAT,
                message = ValidationErrors.INVALID_LICENCE_PLATE_FORMAT)
        String licensePlate,

        @NotNull
        @Size(max = 30, message = ValidationErrors.INVALID_LENGTH)
        String make,

        @NotNull
        @Size(max = 50, message = ValidationErrors.INVALID_LENGTH)
        String model,

        @NotNull
        @Size(max = 20, message = ValidationErrors.INVALID_LENGTH)
        String color
) {
}
