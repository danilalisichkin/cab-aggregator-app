package com.cabaggregator.driverservice.core.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class MultiErrorResponse {
    private String cause;
    private Map<String, String> messages;
}
