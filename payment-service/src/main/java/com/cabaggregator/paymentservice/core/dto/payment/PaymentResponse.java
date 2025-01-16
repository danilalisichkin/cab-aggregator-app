package com.cabaggregator.paymentservice.core.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with payment")
public record PaymentResponse(
        String paymentIntentId,
        String clientSecret
) {
}
