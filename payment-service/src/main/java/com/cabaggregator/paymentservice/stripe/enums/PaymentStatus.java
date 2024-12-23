package com.cabaggregator.paymentservice.stripe.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    CANCELED("canceled"),
    PROCESSING("processing"),
    REQUIRES_ACTION("requires_action"),
    REQUIRES_CAPTURE("requires_capture"),
    REQUIRES_CONFIRMATION("requires_confirmation"),
    REQUIRES_PAYMENT_METHOD("requires_payment_method"),
    SUCCEEDED("succeeded");

    private final String value;
}
