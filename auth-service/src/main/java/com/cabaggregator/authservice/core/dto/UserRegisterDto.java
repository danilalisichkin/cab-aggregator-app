package com.cabaggregator.authservice.core.dto;

import com.cabaggregator.authservice.core.constant.ValidationErrors;
import com.cabaggregator.authservice.core.constant.ValidationRegex;
import com.cabaggregator.authservice.core.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDto(
        @NotEmpty
        @Pattern(regexp = ValidationRegex.PHONE_BELARUS_FORMAT,
                message = ValidationErrors.INVALID_PHONE_FORMAT)
        String phoneNumber,

        @Email
        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String email,

        @NotEmpty
        @Size(min = 4, max = 40, message = ValidationErrors.INVALID_STRING_LENGTH)
        String password,

        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String firstName,

        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String lastName,

        @NotNull
        UserRole role
) {
}
