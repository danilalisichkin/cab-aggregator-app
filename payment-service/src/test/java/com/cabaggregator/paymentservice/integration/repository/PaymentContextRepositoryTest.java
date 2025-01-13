package com.cabaggregator.paymentservice.integration.repository;

import com.cabaggregator.paymentservice.config.AbstractIntegrationTest;
import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.repository.PaymentContextRepository;
import com.cabaggregator.paymentservice.util.PaymentContextTestUtil;
import com.cabaggregator.paymentservice.util.PaymentTestUtil;
import com.cabaggregator.paymentservice.util.StripeTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Sql(scripts = {
        "classpath:/postgresql/import_payment_accounts.sql",
        "classpath:/postgresql/import_payments.sql",
        "classpath:/postgresql/import_payment_contexts.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentContextRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private PaymentContextRepository paymentContextRepository;

    @Test
    void findByPayment_ShouldReturnPaymentContext_WhenPaymentExists() {
        PaymentContext paymentContext = PaymentContextTestUtil.buildDefaultPaymentContext();

        Optional<PaymentContext> actual =
                paymentContextRepository.findByPayment(
                        paymentContext.getPayment());

        assertThat(actual).isPresent();
        assertThat(actual.get().getId()).isEqualTo(paymentContext.getId());
    }

    @Test
    void findByPayment_ShouldReturnEmptyOptional_WhenPaymentDoesNotExist() {
        Payment notExistingPayment = PaymentTestUtil.buildDefaultPayment().toBuilder()
                .paymentIntentId(StripeTestUtil.NOT_EXISTING_INTENT_ID)
                .build();

        Optional<PaymentContext> actual = paymentContextRepository.findByPayment(notExistingPayment);

        assertThat(actual).isEmpty();
    }

    @Test
    void findAllByTypeAndContextId_ShouldReturnPaymentContext_WhenTypeAndContextIdAreEqualToProvided() {
        int expectedListSize = 1;
        PaymentContext paymentContext = PaymentContextTestUtil.buildDefaultPaymentContext();

        List<PaymentContext> actual =
                paymentContextRepository.findAllByTypeAndContextId(
                        paymentContext.getType(),
                        paymentContext.getContextId());

        assertThat(actual)
                .isNotEmpty()
                .hasSize(expectedListSize);
        assertThat(actual.getFirst().getId()).isEqualTo(paymentContext.getId());
    }

    @Test
    void findAllByTypeAndContextId_ShouldReturnEmptyList_WhenContextIdIsNotEqualToProvided() {
        List<PaymentContext> actual =
                paymentContextRepository.findAllByTypeAndContextId(
                        PaymentContextTestUtil.TYPE,
                        PaymentContextTestUtil.NOT_EXISTING_CONTEXT_ID);

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }
}
