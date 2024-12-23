package com.cabaggregator.paymentservice.util;

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
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();

    public static Payment.PaymentBuilder getPaymentBuilder() {
        return Payment.builder()
                .paymentIntentId(StripeTestUtil.INTENT_ID)
                .paymentAccount(PaymentAccountTestUtil.getPaymentAccountBuilder().build())
                .status(StripeTestUtil.STATUS)
                .createdAt(CREATED_AT);
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
