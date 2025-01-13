package com.cabaggregator.payoutservice.core.dto;

import com.cabaggregator.payoutservice.core.constant.ValidationErrors;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Entry to create new balance operation")
public record BalanceOperationAddingDto(
        @NotNull
        Long amount,

        @NotEmpty
        @Size(max = 100, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String transcript
) {
}
