package com.cabaggregator.promocodeservice.core.dto.error;

import java.util.List;
import java.util.Map;

public record MultiErrorResponse(
        String cause,
        Map<String, List<String>> messages
) {
}
