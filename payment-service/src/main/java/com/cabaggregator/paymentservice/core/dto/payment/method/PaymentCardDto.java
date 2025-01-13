package com.cabaggregator.paymentservice.core.dto.payment.method;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with payment card data")
public record PaymentCardDto(
        String paymentMethodId,
        String brand,
        String last4Digits
) {
}
