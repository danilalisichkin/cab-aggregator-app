package com.cabaggregator.paymentservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentAccountSortField {
    USER_ID("userId"),
    STRIPE_CUSTOMER_ID("stripeCustomerId"),
    CREATED_AT("createdAt");

    private final String value;
}
