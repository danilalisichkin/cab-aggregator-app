package com.cabaggregator.driverservice.core.dto.error;

import java.util.Map;

public record MultiErrorResponse(
        String cause,
        Map<String, String> messages
) {
}
