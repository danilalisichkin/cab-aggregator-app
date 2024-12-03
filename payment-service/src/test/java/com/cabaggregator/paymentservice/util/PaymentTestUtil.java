package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentTestUtil {
    public static final String INTENT_ID = "pi_1JXXXXXXYYYYYY";
    public static final PaymentStatus STATUS = PaymentStatus.PROCESSING;
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();

    public static final String CLIENT_SECRET = "pi_1JXXXXXXYYYYYY_secret_abc1234567890abcdefg";
    public static final String METHOD_ID = "pm_1JXXXXXXYYYYYY";

    public static final String CARD_BRAND = "visa";
    public static final String CARD_LAST4 = "1111";

    public static Payment.PaymentBuilder getPaymentBuilder() {
        return Payment.builder()
                .paymentIntentId(INTENT_ID)
                .paymentAccount(PaymentAccountTestUtil.getPaymentAccountBuilder().build())
                .status(STATUS)
                .createdAt(CREATED_AT);
    }

    public static PaymentIntent buildPaymentIntent() {
        PaymentIntent paymentIntent = new PaymentIntent();
        paymentIntent.setId(INTENT_ID);
        paymentIntent.setClientSecret(CLIENT_SECRET);

        return paymentIntent;
    }

    public static PaymentResponse buildPaymentResponse() {
        return new PaymentResponse(
                INTENT_ID,
                CLIENT_SECRET);
    }

    public static PaymentMethod buildPaymentMethod() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(METHOD_ID);

        PaymentMethod.Card card = new PaymentMethod.Card();
        card.setBrand(CARD_BRAND);
        card.setLast4(CARD_LAST4);

        paymentMethod.setCard(card);

        return paymentMethod;
    }

    public static PaymentCardDto buildPaymentCardDto() {
        return new PaymentCardDto(
                METHOD_ID,
                CARD_BRAND,
                CARD_LAST4);
    }
}
