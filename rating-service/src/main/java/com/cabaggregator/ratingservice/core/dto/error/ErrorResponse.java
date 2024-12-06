package com.cabaggregator.ratingservice.core.dto.error;

public record ErrorResponse(
        String cause,
        String message
) {
}
