package com.cabaggregator.pricecalculationservice.core.dto;

import com.cabaggregator.pricecalculationservice.core.constant.ValidationErrors;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PriceCalculationRequest(
        @NotNull
        @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
        Long distance,

        @NotNull
        @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
        Long duration,

        @NotEmpty(message = ValidationErrors.STRING_IS_EMPTY)
        String fare
) {
}
