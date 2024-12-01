package com.cabaggregator.paymentservice.integration.repository;

import com.cabaggregator.paymentservice.config.AbstractIntegrationTest;
import com.cabaggregator.paymentservice.entity.PaymentAccount;
import com.cabaggregator.paymentservice.repository.PaymentAccountRepository;
import com.cabaggregator.paymentservice.util.PaymentAccountTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentAccountRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private PaymentAccountRepository paymentAccountRepository;

    @Test
    void existsByStripeCustomerId_ShouldReturnTrue_WhenCustomerExists() {
        PaymentAccount paymentAccount =
                PaymentAccountTestUtil
                        .getPaymentAccountBuilder()
                        .build();

        paymentAccountRepository.save(paymentAccount);

        boolean actual =
                paymentAccountRepository.existsByStripeCustomerId(
                        paymentAccount.getStripeCustomerId());

        assertThat(actual).isTrue();
    }

    @Test
    void existsByStripeCustomerId_ShouldReturnFalse_WhenCustomerDoesNotExist() {
        boolean actual =
                paymentAccountRepository.existsByStripeCustomerId(
                        PaymentAccountTestUtil.NOT_EXISTING_STRIPE_CUSTOMER_ID);

        assertThat(actual).isFalse();
    }
}
