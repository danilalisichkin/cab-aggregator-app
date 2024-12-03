package com.cabaggregator.paymentservice.core.dto.payment;

public record PaymentResponse(
        String paymentIntentId,
        String clientSecret
) {
}
