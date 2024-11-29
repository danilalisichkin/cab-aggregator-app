package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.entity.PaymentAccount;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentAccountTestUtil {
    public static final UUID ID = UUID.fromString("4665e57c-884a-433d-8fd2-55078f29eab9");
    public static final String STRIPE_CUSTOMER_ID = "cus_J2Y3Z4A5B6C7D8E9F0GHIJKL";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();

    public static PaymentAccount.PaymentAccountBuilder getPaymentAccountBuilder() {
        return PaymentAccount.builder()
                .id(ID)
                .stripeCustomerId(STRIPE_CUSTOMER_ID)
                .createdAt(CREATED_AT);
    }
}
