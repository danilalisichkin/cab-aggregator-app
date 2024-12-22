package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import com.cabaggregator.paymentservice.stripe.enums.PaymentMethodTypes;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StripeTestUtil {
    public static final String INTENT_ID = "pi_1JXXXXXXYYYYYY";
    public static final PaymentStatus STATUS = PaymentStatus.PROCESSING;
    public static final String CLIENT_SECRET = "pi_1JXXXXXXYYYYYY_secret_abc1234567890abcdefg";
    public static final String METHOD_ID = "pm_1JXXXXXXYYYYYY";
    public static final String CARD_BRAND = "visa";
    public static final String CARD_LAST4 = "1111";

    public static final String NOT_EXISTING_INTENT_ID = "not_existing_intent_id";

    public static PaymentIntent buildPaymentIntent() {
        PaymentIntent paymentIntent = new PaymentIntent();
        paymentIntent.setId(INTENT_ID);
        paymentIntent.setClientSecret(CLIENT_SECRET);
        paymentIntent.setStatus(STATUS.name().toLowerCase());

        return paymentIntent;
    }

    public static PaymentMethod buildPaymentMethod() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(METHOD_ID);
        paymentMethod.setType(PaymentMethodTypes.CARD);

        PaymentMethod.Card card = new PaymentMethod.Card();
        card.setBrand(CARD_BRAND);
        card.setLast4(CARD_LAST4);

        paymentMethod.setCard(card);

        return paymentMethod;
    }
}
