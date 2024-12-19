package com.cabaggregator.payoutservice.unit.validator;

import com.cabaggregator.payoutservice.exception.DataUniquenessConflictException;
import com.cabaggregator.payoutservice.repository.PayoutAccountRepository;
import com.cabaggregator.payoutservice.util.PayoutAccountTestUtil;
import com.cabaggregator.payoutservice.util.StripeTestUtil;
import com.cabaggregator.payoutservice.validator.PayoutAccountValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PayoutAccountValidatorTest {
    @InjectMocks
    private PayoutAccountValidator payoutAccountValidator;

    @Mock
    private PayoutAccountRepository payoutAccountRepository;

    @Test
    void validateStripeAccountUniqueness_ShouldNotThrowException_WhenStripeAccountNotUsedYet() {
        String stripeAccountId = StripeTestUtil.STRIPE_ACCOUNT_ID;
        when(payoutAccountRepository.existsByStripeAccountId(stripeAccountId))
                .thenReturn(false);

        assertThatCode(
                () -> payoutAccountValidator.validateStripeAccountUniqueness(stripeAccountId))
                .doesNotThrowAnyException();

        verify(payoutAccountRepository).existsByStripeAccountId(stripeAccountId);
    }

    @Test
    void validateStripeAccountUniqueness_ShouldThrowDataUniquenessConflictException_WhenStripeAccountAlreadyUsed() {
        String stripeAccountId = StripeTestUtil.STRIPE_ACCOUNT_ID;
        when(payoutAccountRepository.existsByStripeAccountId(stripeAccountId))
                .thenReturn(true);

        assertThatThrownBy(
                () -> payoutAccountValidator.validateStripeAccountUniqueness(stripeAccountId))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(payoutAccountRepository).existsByStripeAccountId(stripeAccountId);
    }

    @Test
    void validateIdUniqueness_ShouldNotThrowException_WhenIdNotUsed() {
        UUID id = PayoutAccountTestUtil.ID;
        when(payoutAccountRepository.existsById(id))
                .thenReturn(false);

        assertThatCode(
                () -> payoutAccountValidator.validateIdUniqueness(id))
                .doesNotThrowAnyException();

        verify(payoutAccountRepository).existsById(id);
    }

    @Test
    void validateIdUniqueness_ShouldThrowDataUniquenessException_WhenIdAlreadyUsed() {
        UUID id = PayoutAccountTestUtil.ID;
        when(payoutAccountRepository.existsById(id))
                .thenReturn(true);

        assertThatThrownBy(
                () -> payoutAccountValidator.validateIdUniqueness(id))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(payoutAccountRepository).existsById(id);
    }
}
