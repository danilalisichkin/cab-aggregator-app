package com.cabaggregator.payoutservice.core.dto;

import com.cabaggregator.payoutservice.core.constant.ValidationErrors;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PayoutAccountAddingDto(
        @NotNull
        UUID id,

        @NotEmpty
        @Size(max = 30, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String stripeAccountId
) {
}
