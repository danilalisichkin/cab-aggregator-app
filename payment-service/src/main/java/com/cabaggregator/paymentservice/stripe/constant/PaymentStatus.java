package com.cabaggregator.paymentservice.stripe.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentStatus {
    public static final String CANCELED = "canceled";
    public static final String PROCESSING = "processing";
    public static final String REQUIRES_ACTION = "requires_action";
    public static final String REQUIRES_CAPTURE = "requires_capture";
    public static final String REQUIRES_CONFIRMATION = "requires_confirmation";
    public static final String REQUIRES_PAYMENT_METHOD = "requires_payment_method";
    public static final String SUCCEEDED = "succeeded";
}
