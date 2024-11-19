package com.cabaggregator.driverservice.core.dto.driver;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.constant.ValidationRegex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

public record DriverAddingDto(
        @NotEmpty
        @UUID(message = ValidationErrors.INVALID_UUID_FORMAT)
        String id,

        @NotEmpty
        @Pattern(regexp = ValidationRegex.PHONE_BELARUS_FORMAT,
                message = ValidationErrors.INVALID_PHONE_FORMAT)
        String phoneNumber,

        @Email
        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String email,

        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String firstName,

        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String lastName,

        @NotNull
        Long carId
) {
}
