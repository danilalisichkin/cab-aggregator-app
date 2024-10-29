package com.cabaggregator.driverservice.core.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Server response containing several interconnected errors")
public class MultiErrorResponse {
    private String cause;
    private Map<String, String> messages;
}
