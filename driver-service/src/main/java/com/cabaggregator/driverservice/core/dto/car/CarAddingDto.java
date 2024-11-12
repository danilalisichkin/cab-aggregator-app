package com.cabaggregator.driverservice.core.dto.car;

import com.cabaggregator.driverservice.core.constant.MessageKeys;
import com.cabaggregator.driverservice.core.constant.ValidationRegex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CarAddingDto(
        @NotNull
        @Pattern(regexp = ValidationRegex.LICENCE_PLATE_BELARUS_FORMAT,
                message = MessageKeys.ValidationErrors.INVALID_LICENCE_PLATE_FORMAT)
        String licensePlate,

        @NotNull
        @Size(max = 30, message = MessageKeys.ValidationErrors.INVALID_LENGTH)
        String make,

        @NotNull
        @Size(max = 50, message = MessageKeys.ValidationErrors.INVALID_LENGTH)
        String model,

        @NotNull
        @Size(max = 20, message = MessageKeys.ValidationErrors.INVALID_LENGTH)
        String color
) {
}
