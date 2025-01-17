package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentDto;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentRequest;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.entity.Payment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentTestUtil {
    public static final Long UNIT_AMOUNT = 500L;
    public static final LocalDateTime CREATED_AT = LocalDateTime.parse("2025-02-01T11:44:00");

    public static Payment buildDefaultPayment() {
        return Payment.builder()
                .paymentIntentId(StripeTestUtil.INTENT_ID)
                .paymentAccount(PaymentAccountTestUtil.buildDefaultPaymentAccount())
                .status(StripeTestUtil.STATUS)
                .createdAt(CREATED_AT)
                .build();
    }

    public static PaymentDto buildPaymentDto() {
        return new PaymentDto(
                StripeTestUtil.INTENT_ID,
                PaymentAccountTestUtil.ID,
                StripeTestUtil.STATUS,
                CREATED_AT,
                PaymentContextTestUtil.TYPE,
                PaymentContextTestUtil.CONTEXT_ID);
    }

    public static PaymentRequest buildPaymentRequest() {
        return new PaymentRequest(
                PaymentAccountTestUtil.ID,
                UNIT_AMOUNT,
                StripeTestUtil.METHOD_ID,
                PaymentContextTestUtil.TYPE,
                PaymentContextTestUtil.CONTEXT_ID);
    }

    public static PaymentResponse buildPaymentResponse() {
        return new PaymentResponse(
                StripeTestUtil.INTENT_ID,
                StripeTestUtil.CLIENT_SECRET);
    }

    public static PaymentCardDto buildPaymentCardDto() {
        return new PaymentCardDto(
                StripeTestUtil.METHOD_ID,
                StripeTestUtil.CARD_BRAND,
                StripeTestUtil.CARD_LAST4);
    }
}
