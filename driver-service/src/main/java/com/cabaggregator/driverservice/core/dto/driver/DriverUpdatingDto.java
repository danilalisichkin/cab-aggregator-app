package com.cabaggregator.driverservice.core.dto.driver;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.constant.ValidationRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Entry to update existing driver")
public record DriverUpdatingDto(
        @NotEmpty
        @Pattern(regexp = ValidationRegex.PHONE_BELARUS_FORMAT,
                message = ValidationErrors.INVALID_PHONE_FORMAT)
        String phoneNumber,

        @NotEmpty
        @Email
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String email,

        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String firstName,

        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String lastName,

        @NotNull
        @Min(value = 0, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
        @Max(value = 5, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
        Double rating
) {
}
