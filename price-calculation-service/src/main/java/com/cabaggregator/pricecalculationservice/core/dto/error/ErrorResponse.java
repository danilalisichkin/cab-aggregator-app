package com.cabaggregator.pricecalculationservice.core.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response containing single error")
public record ErrorResponse(
        String cause,
        String message
) {
}
