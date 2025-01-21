package com.cabaggregator.rideservice.kafka.dto;

import java.util.UUID;

public record BalanceOperationRequest(
        UUID accountId,
        Long amount,
        String transcript
) {
}
