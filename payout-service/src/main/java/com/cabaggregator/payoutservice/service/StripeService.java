package com.cabaggregator.payoutservice.service;

import com.stripe.model.Account;

public interface StripeService {
    Account retrieveAccount(String accountId);

    void createPayout(Account account, Long amount);
}
