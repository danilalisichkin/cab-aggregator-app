package com.cabaggregator.payoutservice.integration.repository;

import com.cabaggregator.payoutservice.config.AbstractIntegrationTest;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import com.cabaggregator.payoutservice.repository.BalanceOperationRepository;
import com.cabaggregator.payoutservice.util.BalanceOperationTestUtil;
import com.cabaggregator.payoutservice.util.PayoutAccountTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Sql(scripts = {
        "classpath:/postgresql/import_payout_accounts.sql",
        "classpath:/postgresql/import_balance_operations.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {
        "classpath:/postgresql/clear_balance_operations.sql",
        "classpath:/postgresql/clear_payout_accounts.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BalanceOperationRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private BalanceOperationRepository balanceOperationRepository;

    @Test
    void getBalance_ShouldReturnAccountBalance_WhenAccountExists() {
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();

        Long actual = balanceOperationRepository.getBalance(payoutAccount);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(PayoutAccountTestUtil.COMPUTED_BALANCE);
    }

    @Test
    void getBalance_ShouldReturnNull_WhenAccountDoesNotExist() {
        PayoutAccount notExistingPayoutAccount = PayoutAccountTestUtil.getNotExistingPayoutAccount();

        Long actual = balanceOperationRepository.getBalance(notExistingPayoutAccount);

        assertThat(actual).isNull();
    }

    @Test
    void findAllByPayoutAccountAndCreatedAtBetween_ShouldReturnPageOfBalanceOperations_WhenFieldsMatchToProvided() {
        int pageNumber = 0, pageSize = 10, expectedContentSize = 1;
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        BalanceOperation balanceOperation =
                BalanceOperationTestUtil.getBalanceOperationBuilder()
                        .payoutAccount(payoutAccount)
                        .build();

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndCreatedAtBetween(
                        payoutAccount,
                        PageRequest.of(pageNumber, pageSize),
                        BalanceOperationTestUtil.TIME_BEFORE_CREATION,
                        BalanceOperationTestUtil.TIME_AFTER_CREATION);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(expectedContentSize)
                .contains(balanceOperation);
    }

    @Test
    void findAllByPayoutAccountAndCreatedAtBetween_ShouldReturnEmptyPage_WhenAccountIsNotEqualToProvided() {
        int pageNumber = 0, pageSize = 10;
        PayoutAccount notExistingPayoutAccount = PayoutAccountTestUtil.getNotExistingPayoutAccount();

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndCreatedAtBetween(
                        notExistingPayoutAccount,
                        PageRequest.of(pageNumber, pageSize),
                        BalanceOperationTestUtil.TIME_BEFORE_CREATION,
                        BalanceOperationTestUtil.TIME_AFTER_CREATION);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByPayoutAccountAndCreatedAtBetween_ShouldReturnEmptyPage_WhenCreatedAtIsNotBetweenProvided() {
        int pageNumber = 0, pageSize = 10;
        PayoutAccount notExistingPayoutAccount = PayoutAccountTestUtil.getNotExistingPayoutAccount();

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndCreatedAtBetween(
                        notExistingPayoutAccount,
                        PageRequest.of(pageNumber, pageSize),
                        BalanceOperationTestUtil.TIME_INTERVAL_BEFORE_CREATION_START,
                        BalanceOperationTestUtil.TIME_INTERVAL_BEFORE_CREATION_END);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByPayoutAccountAndTypeAndCreatedAtBetween_ShouldReturnPageOfBalanceOperations_WhenFieldsMatchToProvided() {
        int pageNumber = 0, pageSize = 10, expectedContentSize = 1;
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        BalanceOperation balanceOperation =
                BalanceOperationTestUtil.getBalanceOperationBuilder()
                        .payoutAccount(payoutAccount)
                        .build();

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndTypeAndCreatedAtBetween(
                        payoutAccount,
                        PageRequest.of(pageNumber, pageSize),
                        BalanceOperationTestUtil.TYPE,
                        BalanceOperationTestUtil.TIME_BEFORE_CREATION,
                        BalanceOperationTestUtil.TIME_AFTER_CREATION);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(expectedContentSize)
                .contains(balanceOperation);
    }

    @Test
    void findAllByPayoutAccountAndTypeAndCreatedAtBetween_ShouldReturnEmptyPage_WhenAccountIsNotEqualToProvided() {
        int pageNumber = 0, pageSize = 10;
        PayoutAccount notExistingAccount = PayoutAccountTestUtil.getNotExistingPayoutAccount();

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndTypeAndCreatedAtBetween(
                        notExistingAccount,
                        PageRequest.of(pageNumber, pageSize),
                        BalanceOperationTestUtil.TYPE,
                        BalanceOperationTestUtil.TIME_BEFORE_CREATION,
                        BalanceOperationTestUtil.TIME_AFTER_CREATION);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByPayoutAccountAndTypeAndCreatedAtBetween_ShouldReturnEmptyPage_WhenTypeIsNotEqualToProvided() {
        int pageNumber = 0, pageSize = 10;
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndTypeAndCreatedAtBetween(
                        payoutAccount,
                        PageRequest.of(pageNumber, pageSize),
                        OperationType.WITHDRAWAL,
                        BalanceOperationTestUtil.TIME_BEFORE_CREATION,
                        BalanceOperationTestUtil.TIME_AFTER_CREATION);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByPayoutAccountAndTypeAndCreatedAtBetween_ShouldReturnEmptyPage_WhenCreatedAtIsNotBetweenProvided() {
        int pageNumber = 0, pageSize = 10;
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndTypeAndCreatedAtBetween(
                        payoutAccount,
                        PageRequest.of(pageNumber, pageSize),
                        BalanceOperationTestUtil.TYPE,
                        BalanceOperationTestUtil.TIME_INTERVAL_BEFORE_CREATION_START,
                        BalanceOperationTestUtil.TIME_INTERVAL_BEFORE_CREATION_END);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }
}