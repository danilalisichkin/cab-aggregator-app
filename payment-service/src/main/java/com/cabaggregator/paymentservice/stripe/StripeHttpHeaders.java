package com.cabaggregator.paymentservice.stripe;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StripeHttpHeaders {
    public static final String STRIPE_SIGNATURE = "Stripe-Signature";
}
