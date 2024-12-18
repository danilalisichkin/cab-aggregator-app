package com.cabaggregator.payoutservice.service.impl;

import com.cabaggregator.payoutservice.core.constant.ApplicationMessages;
import com.cabaggregator.payoutservice.exception.InternalErrorException;
import com.cabaggregator.payoutservice.exception.ResourceNotFoundException;
import com.cabaggregator.payoutservice.service.StripeService;
import com.cabaggregator.payoutservice.stripe.constant.Currency;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Payout;
import com.stripe.param.PayoutCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

    @Override
    public Account retrieveAccount(String accountId) {
        try {
            return Account.retrieve(accountId);
        } catch (StripeException e) {
            throw new ResourceNotFoundException(
                    ApplicationMessages.STRIPE_ACCOUNT_WITH_ID_NOT_FOUND,
                    accountId);
        }
    }

    @Override
    public void createPayout(Account account, Long amount) {
        try {
            PayoutCreateParams createParams =
                    PayoutCreateParams.builder()
                            .setAmount(amount)
                            .setCurrency(Currency.USD)
                            .build();

            Payout.create(createParams);
        } catch (StripeException e) {
            throw new InternalErrorException(e);
        }
    }
}
