package com.cabaggregator.pricecalculationservice.core.dto.error;

public record ErrorResponse(
        String cause,
        String message
) {
}
