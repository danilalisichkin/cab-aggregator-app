package com.cabaggregator.paymentservice.validator;

import com.cabaggregator.paymentservice.core.constant.ApplicationMessages;
import com.cabaggregator.paymentservice.exception.DataUniquenessConflictException;
import com.cabaggregator.paymentservice.repository.PaymentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentAccountValidator {
    private final PaymentAccountRepository paymentAccountRepository;

    public void validateIdUniqueness(UUID id) {
        if (paymentAccountRepository.existsById(id)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.ACCOUNT_WITH_ID_ALREADY_EXISTS,
                    id);
        }
    }

    public void validateStripeCustomerIdUniqueness(String stripeCustomerId) {
        if (paymentAccountRepository.existsByStripeCustomerId(stripeCustomerId)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.STRIPE_CUSTOMER_ID_ALREADY_USED,
                    stripeCustomerId);
        }
    }
}
