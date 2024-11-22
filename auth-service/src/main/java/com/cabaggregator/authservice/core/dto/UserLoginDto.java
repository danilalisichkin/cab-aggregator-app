package com.cabaggregator.authservice.core.dto;

import com.cabaggregator.authservice.core.constant.ValidationErrors;
import com.cabaggregator.authservice.validation.Identifier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Entry to sign in existing user in application")
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
