package com.cabaggregator.payoutservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String PAYOUT_ACCOUNT_WITH_STRIPE_ACCOUNT_ALREADY_EXISTS
            = "error.account.with.stripe.account.already.exists";
    public static final String PAYOUT_ACCOUNT_WITH_ID_NOT_FOUND = "error.account.with.id.not.found";
    public static final String PAYOUT_ACCOUNT_WITH_ID_ALREADY_EXISTS = "error.account.with.id.already.exists";
    public static final String INSUFFICIENT_FUNDS_IN_THE_ACCOUNT = "error.insufficient.funds.in.account";
    public static final String STRIPE_ACCOUNT_WITH_ID_NOT_FOUND = "error.stripe.account.with.id.not.found";
    public static final String WITHDRAWING_AMOUNT_MUST_BE_NEGATIVE = "error.withdrawal.amount.not.negative";
    public static final String DEPOSIT_AMOUNT_MUST_BE_POSITIVE = "error.deposit.amount.not.positive";
    public static final String TIME_AFTER_PRESENT = "error.time.after.present";
    public static final String INVALID_TIME_RANGE = "error.invalid.time.range";
    public static final String CANT_ACCESS_ACCOUNT_OF_OTHER_USER = "error.cant.access.account.of.other.user";
}
