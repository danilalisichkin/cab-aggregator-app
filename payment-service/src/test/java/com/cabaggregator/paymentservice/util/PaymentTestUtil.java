package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentTestUtil {
    public static final String INTENT_ID = "pi_1J2Y3Z4A5B6C7D8E9F0GHIJKL";
    public static final PaymentStatus STATUS = PaymentStatus.PROCESSING;
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();

    public static Payment.PaymentBuilder getPaymentBuilder() {
        return Payment.builder()
                .paymentIntentId(INTENT_ID)
                .paymentAccount(PaymentAccountTestUtil.getPaymentAccountBuilder().build())
                .status(STATUS)
                .createdAt(CREATED_AT);
    }
}
