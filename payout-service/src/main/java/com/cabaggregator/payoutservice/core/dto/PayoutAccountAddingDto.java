package com.cabaggregator.payoutservice.core.dto;

import com.cabaggregator.payoutservice.core.constant.ValidationErrors;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Entry to create payout account")
public record PayoutAccountAddingDto(
        @NotNull
        UUID id,

        @NotEmpty
        @Size(max = 30, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String stripeAccountId
) {
}
