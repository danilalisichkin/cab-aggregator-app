package com.cabaggregator.payoutservice.util;

import com.cabaggregator.payoutservice.entity.PayoutAccount;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PayoutAccountTestUtil {
    public static final UUID ID = UUID.fromString("4665e57c-884a-433d-8fd2-55078f29eab9");
    public static final String STRIPE_ACCOUNT_ID = "acct_1J2Y3Z4A5B6C7D8E9F0GHIJKL";
    public static final Long BALANCE = 250L;
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final Boolean ACTIVE = true;

    public static final UUID ID2 = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
    public static final String STRIPE_ACCOUNT_ID2 = "acct_2M4N5O6P7Q8R9S0T1U2VWXYZK";

    public static PayoutAccount.PayoutAccountBuilder getPayoutAccountBuilder() {
        return PayoutAccount.builder()
                .id(ID)
                .stripeAccountId(STRIPE_ACCOUNT_ID)
                .balance(BALANCE)
                .createdAt(CREATED_AT)
                .active(ACTIVE);
    }

    public static PayoutAccount buildPayoutAccount1() {
        return getPayoutAccountBuilder()
                .id(ID)
                .stripeAccountId(STRIPE_ACCOUNT_ID)
                .build();
    }

    public static PayoutAccount buildPayoutAccount2() {
        return getPayoutAccountBuilder()
                .id(ID2)
                .stripeAccountId(STRIPE_ACCOUNT_ID2)
                .build();
    }
}
