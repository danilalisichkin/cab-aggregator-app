package com.cabaggregator.authservice.core.dto.error;

public record ErrorResponse(
        String message,
        String cause
) {
}
