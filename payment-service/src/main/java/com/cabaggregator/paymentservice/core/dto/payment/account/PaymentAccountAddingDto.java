package com.cabaggregator.paymentservice.core.dto.payment.account;

import com.cabaggregator.paymentservice.core.constant.ValidationErrors;
import com.cabaggregator.paymentservice.core.constant.ValidationRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Entry to create new payment account")
public record PaymentAccountAddingDto(
        @NotNull
        UUID id,

        @NotEmpty
        @Pattern(regexp = ValidationRegex.PHONE_BELARUS_FORMAT,
                message = ValidationErrors.INVALID_PHONE_FORMAT)
        String phoneNumber,

        @NotEmpty
        @Email
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String email,

        @NotEmpty(message = ValidationErrors.STRING_IS_EMPTY)
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String firstName,

        @NotEmpty(message = ValidationErrors.STRING_IS_EMPTY)
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String lastName
) {
}
