package com.cabaggregator.paymentservice.core.dto.error;

public record ErrorResponse(
        String cause,
        String message
) {
}
