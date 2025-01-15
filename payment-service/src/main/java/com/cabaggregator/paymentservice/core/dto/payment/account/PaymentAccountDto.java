package com.cabaggregator.paymentservice.core.dto.payment.account;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Server response with payment account data")
public record PaymentAccountDto(
        UUID id,
        String stripeCustomerId,
        LocalDateTime createdAt
) {
}
