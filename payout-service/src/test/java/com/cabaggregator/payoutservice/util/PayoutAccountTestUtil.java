package com.cabaggregator.payoutservice.util;

import com.cabaggregator.payoutservice.core.dto.PayoutAccountAddingDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountDto;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PayoutAccountTestUtil {
    public static final UUID ID = UUID.fromString("4665e57c-884a-433d-8fd2-55078f29eab9");
    public static final LocalDateTime CREATED_AT = LocalDateTime.parse("2024-12-01T12:00:00");
    public static final Boolean ACTIVE = true;

    public static final Long COMPUTED_BALANCE = 1000L;

    public static final UUID NOT_EXISTING_ID = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

    public static PayoutAccount.PayoutAccountBuilder getPayoutAccountBuilder() {
        return PayoutAccount.builder()
                .id(ID)
                .stripeAccountId(StripeTestUtil.STRIPE_ACCOUNT_ID)
                .createdAt(CREATED_AT)
                .active(ACTIVE);
    }

    public static PayoutAccount getNotExistingPayoutAccount() {
        return getPayoutAccountBuilder()
                .id(NOT_EXISTING_ID)
                .stripeAccountId(StripeTestUtil.NOT_EXISTING_STRIPE_ACCOUNT_ID)
                .build();
    }

    public static PayoutAccountDto buildPayoutAccountDto() {
        return new PayoutAccountDto(
                ID,
                StripeTestUtil.STRIPE_ACCOUNT_ID,
                CREATED_AT,
                ACTIVE);
    }

    public static PayoutAccountAddingDto buildPayoutAccountAddingDto() {
        return new PayoutAccountAddingDto(
                ID,
                StripeTestUtil.STRIPE_ACCOUNT_ID);
    }
}
