package com.cabaggregator.paymentservice.core.dto.payment.method;

public record PaymentCardDto(
        String paymentMethodId,
        String brand,
        String last4Digits
) {
}
