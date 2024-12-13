package com.cabaggregator.payoutservice.validator;

import com.cabaggregator.payoutservice.core.constant.ApplicationMessages;
import com.cabaggregator.payoutservice.exception.DataUniquenessConflictException;
import com.cabaggregator.payoutservice.repository.PayoutAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PayoutAccountValidator {
    private final PayoutAccountRepository payoutAccountRepository;

    public void validateStripeAccountUniqueness(String stripeAccountId) {
        if (payoutAccountRepository.existsByStripeAccountId(stripeAccountId)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.PAYOUT_ACCOUNT_WITH_STRIPE_ACCOUNT_ALREADY_EXISTS,
                    stripeAccountId);
        }
    }

    public void validateIdUniqueness(UUID userId) {
        if (payoutAccountRepository.existsById(userId)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.PAYOUT_ACCOUNT_WITH_ID_ALREADY_EXISTS,
                    userId);
        }
    }
}
