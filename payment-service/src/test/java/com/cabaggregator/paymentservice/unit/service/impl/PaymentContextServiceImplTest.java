package com.cabaggregator.paymentservice.unit.service.impl;

import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.exception.ResourceNotFoundException;
import com.cabaggregator.paymentservice.repository.PaymentContextRepository;
import com.cabaggregator.paymentservice.service.impl.PaymentContextServiceImpl;
import com.cabaggregator.paymentservice.util.PaymentContextTestUtil;
import com.cabaggregator.paymentservice.util.PaymentTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PaymentContextServiceImplTest {

    @InjectMocks
    private PaymentContextServiceImpl paymentContextService;

    @Mock
    private PaymentContextRepository paymentContextRepository;

    @Test
    void getPaymentContext_ShouldReturnPaymentContext_WhenContextExists() {
        Payment payment = PaymentTestUtil.getPaymentBuilder().build();
        PaymentContext paymentContext = PaymentContextTestUtil.getPaymentContextBuilder().build();

        when(paymentContextRepository.findByPayment(payment))
                .thenReturn(Optional.of(paymentContext));

        PaymentContext actual = paymentContextService.getPaymentContext(payment);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentContext);

        verify(paymentContextRepository).findByPayment(payment);
    }

    @Test
    void getPaymentContext_ShouldThrowResourceNotFoundException_WhenContextDoesNotExist() {
        Payment payment = PaymentTestUtil.getPaymentBuilder().build();

        when(paymentContextRepository.findByPayment(payment))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> paymentContextService.getPaymentContext(payment))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(paymentContextRepository).findByPayment(payment);
    }
}