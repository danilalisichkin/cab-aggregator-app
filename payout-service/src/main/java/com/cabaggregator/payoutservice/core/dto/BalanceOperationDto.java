package com.cabaggregator.payoutservice.core.dto;

import com.cabaggregator.payoutservice.core.enums.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Server response with stored balance operation data")
public record BalanceOperationDto(
        Long id,
        UUID payoutAccountId,
        Long amount,
        OperationType type,
        String transcript,
        LocalDateTime createdAt
) {
}
