package com.cabaggregator.rideservice.core.dto.error;

public record ErrorResponse(
        String cause,
        String message
) {
}
