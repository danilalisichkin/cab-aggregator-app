package com.cabaggregator.payoutservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Server response with stored payout account data")
public record PayoutAccountDto(
        UUID id,
        String stripeAccountId,
        LocalDateTime createdAt,
        Boolean active
) {
}
