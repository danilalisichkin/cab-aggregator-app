package com.cabaggregator.payoutservice.core.dto;

public record BalanceOperationAddingDto(
        Long amount,
        String transcript
) {
}
