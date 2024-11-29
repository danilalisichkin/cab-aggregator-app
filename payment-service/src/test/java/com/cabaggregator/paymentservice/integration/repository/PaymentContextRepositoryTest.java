package com.cabaggregator.paymentservice.integration.repository;

import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.PaymentAccount;
import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.repository.PaymentAccountRepository;
import com.cabaggregator.paymentservice.repository.PaymentContextRepository;
import com.cabaggregator.paymentservice.repository.PaymentRepository;
import com.cabaggregator.paymentservice.util.PaymentAccountTestUtil;
import com.cabaggregator.paymentservice.util.PaymentContextTestUtil;
import com.cabaggregator.paymentservice.util.PaymentTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaymentContextRepositoryTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private PaymentContextRepository paymentContextRepository;

    @Autowired
    private PaymentAccountRepository paymentAccountRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private PaymentAccount savePaymentAccount() {
        return paymentAccountRepository.save(
                PaymentAccountTestUtil.getPaymentAccountBuilder().build());
    }

    private Payment savePayment(PaymentAccount paymentAccount) {
        return paymentRepository.save(
                PaymentTestUtil.getPaymentBuilder().paymentAccount(paymentAccount).build());
    }

    @Test
    void findByPayment_ShouldReturnPaymentContext_WhenPaymentExists() {
        PaymentAccount paymentAccount = savePaymentAccount();
        Payment payment = savePayment(paymentAccount);

        PaymentContext paymentContext =
                PaymentContextTestUtil.getPaymentContextBuilder()
                        .id(null).payment(payment).build();
        paymentContextRepository.save(paymentContext);

        Optional<PaymentContext> actual =
                paymentContextRepository.findByPayment(paymentContext.getPayment());

        assertThat(actual)
                .isPresent()
                .contains(paymentContext);
    }

    @Test
    void findByPayment_ShouldReturnEmptyOptional_WhenPaymentDoesNotExist() {
        Optional<PaymentContext> actual =
                paymentContextRepository.findByPayment(PaymentTestUtil.getPaymentBuilder().build());

        assertThat(actual).isEmpty();
    }

    @Test
    void findByTypeAndContextId_ShouldReturnPaymentContext_WhenTypeAndContextIdAreEqualToProvided() {
        PaymentAccount paymentAccount = savePaymentAccount();
        Payment payment = savePayment(paymentAccount);

        PaymentContext paymentContext =
                PaymentContextTestUtil.getPaymentContextBuilder()
                        .id(null).payment(payment).build();

        paymentContextRepository.save(paymentContext);

        List<PaymentContext> actual =
                paymentContextRepository.findByTypeAndContextId(
                        paymentContext.getType(), paymentContext.getContextId());

        assertThat(actual)
                .isNotEmpty()
                .hasSize(1)
                .contains(paymentContext);
    }

    @Test
    void findByTypeAndContextId_ShouldReturnEmptyList_WhenContextIdIsNotEqualToProvided() {
        PaymentAccount paymentAccount = savePaymentAccount();
        Payment payment = savePayment(paymentAccount);

        PaymentContext paymentContext =
                PaymentContextTestUtil.getPaymentContextBuilder()
                        .id(null).payment(payment).build();

        List<PaymentContext> actual =
                paymentContextRepository.findByTypeAndContextId(
                        paymentContext.getType(), "nonexistent_context_id");

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }
}
