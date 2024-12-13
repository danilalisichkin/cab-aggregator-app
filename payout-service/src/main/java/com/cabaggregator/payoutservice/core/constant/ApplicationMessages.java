package com.cabaggregator.payoutservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String PAYOUT_ACCOUNT_WITH_STRIPE_ACCOUNT_ALREADY_EXISTS
            = "error.account.with.stripe.account.already.exists";
    public static final String PAYOUT_ACCOUNT_WITH_ID_ALREADY_EXISTS = "error.account.with.id.already.exists";
    public static final String DRIVER_DID_NOT_RETURN_ALL_CASH = "error.driver.did.not.return.all.cash";
    public static final String INSUFFICIENT_FUNDS_IN_THE_ACCOUNT = "error.insufficient.funds.in.account";
    public static final String ERROR_WITHDRAWING_FUNDS = "error.withdrawing.funds";
    public static final String INVALID_REQUEST = "error.invalid.request";
    public static final String WITHDRAWING_AMOUNT_MUST_BE_NEGATIVE = "error.withdrawal.amount.not.negative";
    public static final String DEPOSIT_AMOUNT_MUST_BE_POSITIVE = "error.deposit.amount.not.positive";
}
