package com.cabaggregator.driverservice.core.dto.error;

public record ErrorResponse(
        String message,
        String cause
) {
}
