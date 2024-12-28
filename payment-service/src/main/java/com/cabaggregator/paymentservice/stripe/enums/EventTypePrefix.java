package com.cabaggregator.paymentservice.stripe.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventTypePrefix {
    PAYMENT_INTENT("payment_intent");

    private final String value;
}
