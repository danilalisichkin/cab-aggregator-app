package com.cabaggregator.paymentservice.stripe.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentMethodTypes {
    public static final String CARD = "card";
    public static final String PAYPAL = "paypal";
}
