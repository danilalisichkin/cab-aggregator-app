package com.cabaggregator.payoutservice.core.dto;

import com.cabaggregator.payoutservice.core.enums.OperationType;

import java.time.LocalDateTime;
import java.util.UUID;

public record BalanceOperationDto(
        Long id,
        UUID payoutAccountId,
        Long amount,
        OperationType type,
        String transcript,
        LocalDateTime createdAt
) {
}
