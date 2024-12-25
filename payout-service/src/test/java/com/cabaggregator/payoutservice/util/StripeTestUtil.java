package com.cabaggregator.payoutservice.util;

import com.stripe.model.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StripeTestUtil {
    public static final String STRIPE_ACCOUNT_ID = "acct_1J2Y3Z4A5B6C7D8E9F0GHIJKL";

    public static final String NOT_EXISTING_STRIPE_ACCOUNT_ID = "acct_0000000000000000000000000";

    public static Account buildStripeAccount() {
       Account stripeAccount =  new Account();
       stripeAccount.setId(STRIPE_ACCOUNT_ID);

       return stripeAccount;
    }
}
