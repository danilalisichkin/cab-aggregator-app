package com.cabaggregator.payoutservice.integration.repository;

import com.cabaggregator.payoutservice.config.AbstractIntegrationTest;
import com.cabaggregator.payoutservice.repository.PayoutAccountRepository;
import com.cabaggregator.payoutservice.util.PayoutAccountTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Sql(scripts = {
        "classpath:sql/repository/import_payout_accounts.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {
        "classpath:/sql/repository/clear_payout_accounts.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PayoutAccountRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private PayoutAccountRepository payoutAccountRepository;

    @Test
    void existsByStripeAccountId_ShouldReturnTrue_WhenAccountExists() {
        boolean actual = payoutAccountRepository.existsByStripeAccountId(
                PayoutAccountTestUtil.STRIPE_ACCOUNT_ID);

        assertThat(actual).isTrue();
    }

    @Test
    void existsByStripeAccountId_ShouldReturnFalse_WhenAccountDoesNotExist() {
        boolean actual = payoutAccountRepository.existsByStripeAccountId(
                PayoutAccountTestUtil.NOT_EXISTING_STRIPE_ACCOUNT_ID);

        assertThat(actual).isFalse();
    }
}
