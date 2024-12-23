package com.cabaggregator.paymentservice.stripe.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethodType {
    CARD("card"),
    PAYPAL("paypal");

    private final String value;
}
