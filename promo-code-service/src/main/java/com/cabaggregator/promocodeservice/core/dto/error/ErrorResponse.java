package com.cabaggregator.promocodeservice.core.dto.error;

public record ErrorResponse(
        String message,
        String cause
) {
}
