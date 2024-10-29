package com.cabaggregator.driverservice.core.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Server response containing single error")
public class ErrorResponse {
    private String message;
    private String cause;
}
