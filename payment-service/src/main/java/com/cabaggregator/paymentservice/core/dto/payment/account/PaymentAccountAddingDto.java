package com.cabaggregator.paymentservice.core.dto.payment.account;

import com.cabaggregator.paymentservice.core.constant.ValidationErrors;
import com.cabaggregator.paymentservice.core.constant.ValidationRegex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PaymentAccountAddingDto(
        @NotNull
        UUID id,

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
        String lastName
) {
}
