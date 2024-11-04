package com.cabaggregator.passengerservice.core.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "Server response containing several interconnected errors")
public record MultiErrorResponse(
        String cause,
        Map<String, List<String>> messages
) {
}
