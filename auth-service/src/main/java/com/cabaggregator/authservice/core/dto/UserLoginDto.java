package com.cabaggregator.authservice.core.dto;

import com.cabaggregator.authservice.core.constant.ValidationErrors;
import com.cabaggregator.authservice.validation.Identifier;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginDto(
        @NotEmpty
        @Identifier(message = ValidationErrors.INVALID_LOGIN_IDENTIFIER)
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String identifier,

        @NotEmpty
        @Size(min = 4, max = 40, message = ValidationErrors.INVALID_STRING_LENGTH)
        String password
) {
}
