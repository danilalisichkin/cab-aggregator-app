package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
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

    public static final String USER_PHONE_NUMBER = "375291234567";
    public static final String USER_EMAIL = "user@cabaggregator.com";
    public static final String USER_FIRST_NAME = "Adam";
    public static final String USER_LAST_NAME = "Smith";

    public static final UUID NOT_EXISTING_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String NOT_EXISTING_STRIPE_CUSTOMER_ID = "cus_000000000000000000000000";

    public static PaymentAccount buildDefaultPaymentAccount() {
        return PaymentAccount.builder()
                .id(ID)
                .stripeCustomerId(STRIPE_CUSTOMER_ID)
                .createdAt(CREATED_AT)
                .build();
    }

    public static PaymentAccountDto buildPaymentAccountDto() {
        return new PaymentAccountDto(
                ID,
                STRIPE_CUSTOMER_ID,
                CREATED_AT);
    }

    public static PaymentAccountAddingDto buildPaymentAccountAddingDto() {
        return new PaymentAccountAddingDto(
                ID,
                USER_PHONE_NUMBER,
                USER_EMAIL,
                USER_FIRST_NAME,
                USER_LAST_NAME);
    }
}
