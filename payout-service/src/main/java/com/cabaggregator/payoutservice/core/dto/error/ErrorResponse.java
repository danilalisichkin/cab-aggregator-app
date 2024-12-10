package com.cabaggregator.payoutservice.core.dto.error;

public record ErrorResponse(
        String cause,
        String message
) {
}
