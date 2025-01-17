package com.cabaggregator.driverservice.core.dto.car;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.constant.ValidationRegex;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Entry to add new car with its details")
public record CarAddingDto(
        @NotEmpty
        @Pattern(regexp = ValidationRegex.LICENCE_PLATE_BELARUS_FORMAT,
                message = ValidationErrors.INVALID_LICENCE_PLATE_FORMAT)
        String licensePlate,

        @NotEmpty
        @Size(max = 30, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String make,

        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String model,

        @NotEmpty
        @Size(max = 20, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String color,

        @NotNull
        CarDetailsSettingDto details
) {
}
