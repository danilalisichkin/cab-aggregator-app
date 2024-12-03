package com.cabaggregator.payoutservice.integration.repository;

import com.cabaggregator.payoutservice.config.AbstractIntegrationTest;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import com.cabaggregator.payoutservice.repository.PayoutAccountRepository;
import com.cabaggregator.payoutservice.util.PayoutAccountTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PayoutAccountRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private PayoutAccountRepository payoutAccountRepository;

    @Test
    void existsByStripeAccountId_ShouldReturnTrue_WhenAccountExists() {
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        payoutAccountRepository.save(payoutAccount);

        boolean actual = payoutAccountRepository.existsByStripeAccountId(payoutAccount.getStripeAccountId());

        assertThat(actual).isTrue();
    }

    @Test
    void existsByStripeAccountId_ShouldReturnFalse_WhenAccountDoesNotExist() {
        boolean actual = payoutAccountRepository.existsByStripeAccountId(
                PayoutAccountTestUtil.NOT_EXISTING_STRIPE_ACCOUNT_ID);

        assertThat(actual).isFalse();
    }
}
