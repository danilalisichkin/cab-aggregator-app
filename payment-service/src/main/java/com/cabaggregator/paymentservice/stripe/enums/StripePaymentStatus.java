package com.cabaggregator.paymentservice.stripe.enums;

import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StripePaymentStatus {
    CANCELED("canceled", PaymentStatus.CANCELED),
    CREATED("created", PaymentStatus.PROCESSING),
    PARTIALLY_FUNDED("partially_funded", PaymentStatus.FAILED),
    PAYMENT_FAILED("payment_failed", PaymentStatus.FAILED),
    PROCESSING("processing", PaymentStatus.PROCESSING),
    REQUIRES_ACTION("requires_action", PaymentStatus.PROCESSING),
    SUCCEEDED("succeeded", PaymentStatus.SUCCEEDED);

    private final String value;
    private final PaymentStatus internalStatus;
}
