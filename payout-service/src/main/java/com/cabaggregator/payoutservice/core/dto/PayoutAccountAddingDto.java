package com.cabaggregator.payoutservice.core.dto;

import java.util.UUID;

public record PayoutAccountAddingDto(
        UUID id,
        String stripeAccountId
) {
}
