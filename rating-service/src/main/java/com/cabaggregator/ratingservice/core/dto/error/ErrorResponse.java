package com.cabaggregator.ratingservice.core.dto.error;

public record ErrorResponse(
        String message,
        String cause
) {
}
