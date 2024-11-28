package com.cabaggregator.payoutservice.integration.repository;

import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import com.cabaggregator.payoutservice.entity.enums.OperationType;
import com.cabaggregator.payoutservice.repository.BalanceOperationRepository;
import com.cabaggregator.payoutservice.repository.PayoutAccountRepository;
import com.cabaggregator.payoutservice.util.BalanceOperationTestUtil;
import com.cabaggregator.payoutservice.util.PayoutAccountTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BalanceOperationRepositoryTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private BalanceOperationRepository balanceOperationRepository;

    @Autowired
    private PayoutAccountRepository payoutAccountRepository;

    private PayoutAccount createPayoutAccount1() {
        return payoutAccountRepository.save(
                PayoutAccountTestUtil.buildPayoutAccount1());
    }

    private PayoutAccount createPayoutAccount2() {
        return payoutAccountRepository.save(
                PayoutAccountTestUtil.buildPayoutAccount2());
    }

    private BalanceOperation saveBalanceOperation(PayoutAccount payoutAccount, OperationType type) {
        BalanceOperation operation = BalanceOperationTestUtil.getBalanceOperationBuilder()
                .id(null)
                .payoutAccount(payoutAccount)
                .type(type)
                .build();
        return balanceOperationRepository.save(operation);
    }

    @Test
    void findAllByPayoutAccount_ShouldReturnPageOfBalanceOperations_WhenPayoutAccountIsEqualToProvided() {
        PayoutAccount payoutAccount1 = createPayoutAccount1();
        BalanceOperation operation = saveBalanceOperation(payoutAccount1, OperationType.WITHDRAWAL);

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccount(
                        payoutAccount1, PageRequest.of(0, 10));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(1)
                .contains(operation);
    }

    @Test
    void findAllByPayoutAccount_ShouldReturnEmptyPage_WhenPayoutAccountIsNotEqualToProvided() {
        PayoutAccount payoutAccount1 = createPayoutAccount1();
        PayoutAccount payoutAccount2 = createPayoutAccount2();

        saveBalanceOperation(payoutAccount1, OperationType.DEPOSIT);

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccount(
                        payoutAccount2, PageRequest.of(0, 10));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByPayoutAccountAndType_ShouldReturnPageOfBalanceOperations_WhenPayoutAccountAndTypeAreEqualToProvided() {
        PayoutAccount payoutAccount1 = createPayoutAccount1();
        BalanceOperation withdrawalOperation = saveBalanceOperation(payoutAccount1, OperationType.WITHDRAWAL);

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndType(
                        payoutAccount1, PageRequest.of(0, 10), OperationType.WITHDRAWAL);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(1)
                .contains(withdrawalOperation);
    }

    @Test
    void findAllByPayoutAccountAndType_ShouldReturnEmpty_WhenPayoutAccountIsNotEqualToProvided() {
        PayoutAccount payoutAccount1 = createPayoutAccount1();
        PayoutAccount payoutAccount2 = createPayoutAccount2();

        saveBalanceOperation(payoutAccount1, OperationType.DEPOSIT);

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndType(
                        payoutAccount2, PageRequest.of(0, 10), OperationType.WITHDRAWAL);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByPayoutAccountAndType_ShouldReturnEmpty_WhenOperationTypeIsNotEqualToProvided() {
        PayoutAccount payoutAccount1 = createPayoutAccount1();
        saveBalanceOperation(payoutAccount1, OperationType.DEPOSIT);

        Page<BalanceOperation> actual =
                balanceOperationRepository.findAllByPayoutAccountAndType(
                        payoutAccount1, PageRequest.of(0, 10), OperationType.WITHDRAWAL);

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }
}