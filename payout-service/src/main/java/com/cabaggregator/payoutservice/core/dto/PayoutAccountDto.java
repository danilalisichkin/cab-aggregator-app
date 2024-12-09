package com.cabaggregator.payoutservice.core.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record PayoutAccountDto(
        UUID id,
        String stripeAccountId,
        LocalDateTime createdAt,
        Boolean active
) {
}
