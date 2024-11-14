package com.cabaggregator.driverservice.core.dto.driver;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.constant.ValidationRegex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DriverAddingDto(
        @NotNull
        @Pattern(regexp = ValidationRegex.PHONE_BELARUS_FORMAT,
                message = ValidationErrors.INVALID_PHONE_FORMAT)
        String phoneNumber,

        @NotNull
        @Email
        @Size(max = 50, message = ValidationErrors.INVALID_LENGTH)
        String email,

        @NotNull
        @Size(max = 50, message = ValidationErrors.INVALID_LENGTH)
        String firstName,

        @NotNull
        @Size(max = 50, message = ValidationErrors.INVALID_LENGTH)
        String lastName
) {
}
