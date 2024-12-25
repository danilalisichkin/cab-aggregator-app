package com.cabaggregator.paymentservice.stripe.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    CANCELED("canceled"),
    CREATED("created"),
    PARTIALLY_FUNDED("partially_funded"),
    PAYMENT_FAILED("payment_failed"),
    PROCESSING("processing"),
    REQUIRES_ACTION("requires_action"),
    SUCCEEDED("succeeded");

    private final String value;
}
