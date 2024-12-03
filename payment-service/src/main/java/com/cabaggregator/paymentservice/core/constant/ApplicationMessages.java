package com.cabaggregator.paymentservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String ACCOUNT_WITH_ID_NOT_FOUND = "error.account.with.id.not.found";
    public static final String ACCOUNT_WITH_USER_ID_ALREADY_EXISTS = "error.account.with.user.id.already.exists";
    public static final String STRIPE_CUSTOMER_ID_ALREADY_USED = "error.stripe.customer.id.already.used";
    public static final String CONTEXT_FOR_PAYMENT_WITH_ID_NOT_FOUND = "error.context.for.payment.with.id.not.found";
    public static final String DEFAULT_PAYMENT_METHOD_NOT_CARD = "error.default.payment.method.not.card";
    public static final String PAYMENT_WITH_ID_NOT_FOUND = "error.payment.with.id.not.found";
    public static final String PROVIDED_PAYMENT_METHOD_NOT_CARD = "error.provided.payment.method.not.card";
}