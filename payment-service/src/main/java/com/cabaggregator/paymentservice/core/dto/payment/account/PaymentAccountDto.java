package com.cabaggregator.paymentservice.core.dto.payment.account;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentAccountDto(
        UUID id,
        String stripeCustomerId,
        LocalDateTime createdAt
) {
}
