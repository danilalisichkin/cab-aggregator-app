package com.cabaggregator.paymentservice.unit.service.impl;

import com.cabaggregator.paymentservice.client.RideServiceApiClient;
import com.cabaggregator.paymentservice.core.constant.ApplicationMessages;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentRequest;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import com.cabaggregator.paymentservice.core.mapper.PaymentMapper;
import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.PaymentAccount;
import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;
import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import com.cabaggregator.paymentservice.exception.ResourceNotFoundException;
import com.cabaggregator.paymentservice.repository.PaymentAccountRepository;
import com.cabaggregator.paymentservice.repository.PaymentRepository;
import com.cabaggregator.paymentservice.service.PaymentContextService;
import com.cabaggregator.paymentservice.service.StripeService;
import com.cabaggregator.paymentservice.service.impl.PaymentServiceImpl;
import com.cabaggregator.paymentservice.util.PaymentAccountTestUtil;
import com.cabaggregator.paymentservice.util.PaymentContextTestUtil;
import com.cabaggregator.paymentservice.util.PaymentTestUtil;
import com.cabaggregator.paymentservice.util.StripeTestUtil;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private StripeService stripeService;

    @Mock
    private PaymentContextService paymentContextService;

    @Mock
    private RideServiceApiClient rideServiceApiClient;

    @Mock
    private PaymentAccountRepository paymentAccountRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Test
    void createPayment_ShouldReturnPaymentResponse_WhenPaymentIntentIsCreated() {
        PaymentRequest paymentRequest = PaymentTestUtil.buildPaymentRequest();
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        Customer stripeCustomer = new Customer();
        PaymentIntent paymentIntent = StripeTestUtil.buildPaymentIntent();
        Payment payment = PaymentTestUtil.getPaymentBuilder().build();
        PaymentResponse paymentResponse = PaymentTestUtil.buildPaymentResponse();
        String description = String.format("Ride №%s", paymentRequest.contextId());

        when(paymentAccountRepository.findById(paymentRequest.paymentAccountId()))
                .thenReturn(Optional.of(paymentAccount));
        when(stripeService.retrieveCustomer(paymentAccount.getStripeCustomerId()))
                .thenReturn(stripeCustomer);
        when(stripeService.createPaymentIntent(
                stripeCustomer,
                description,
                paymentRequest.unitAmount(),
                paymentRequest.paymentMethodId()))
                .thenReturn(paymentIntent);
        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);
        doNothing().when(paymentContextService).savePaymentContext(
                payment, PaymentContextType.RIDE, paymentRequest.contextId());
        when(paymentMapper.intentToResponse(paymentIntent))
                .thenReturn(paymentResponse);

        PaymentResponse actual = paymentService.createPayment(paymentRequest);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentResponse);

        verify(paymentAccountRepository).findById(paymentRequest.paymentAccountId());
        verify(stripeService).retrieveCustomer(paymentAccount.getStripeCustomerId());
        verify(stripeService).createPaymentIntent(
                stripeCustomer, description, paymentRequest.unitAmount(), paymentRequest.paymentMethodId());
        verify(paymentRepository).save(any(Payment.class));
        verify(paymentContextService).savePaymentContext(
                payment, PaymentContextType.RIDE, paymentRequest.contextId());
        verify(paymentMapper).intentToResponse(paymentIntent);
    }

    @Test
    void createPayment_ShouldThrowResourceNotFoundException_WhenPaymentAccountNotFound() {
        PaymentRequest paymentRequest = PaymentTestUtil.buildPaymentRequest();

        when(paymentAccountRepository.findById(paymentRequest.paymentAccountId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.createPayment(paymentRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(ApplicationMessages.ACCOUNT_WITH_ID_NOT_FOUND);

        verify(paymentAccountRepository).findById(paymentRequest.paymentAccountId());
        verifyNoInteractions(stripeService, paymentRepository, paymentMapper);
    }

    @Test
    void updatePaymentStatus_ShouldUpdatePaymentStatusAndDoesNotEndRide_WhenPaymentExistsAndStatusNotSucceed() {
        String paymentIntentId = StripeTestUtil.INTENT_ID;
        PaymentStatus paymentStatus = PaymentStatus.PROCESSING;
        Payment payment = PaymentTestUtil.getPaymentBuilder().build();
        Payment updatedPayment = payment.withStatus(paymentStatus);

        when(paymentRepository.findById(paymentIntentId))
                .thenReturn(Optional.of(payment));
        when(paymentRepository.save(updatedPayment))
                .thenReturn(updatedPayment);

        assertThatCode(
                () -> paymentService.updatePaymentStatus(paymentIntentId, paymentStatus))
                .doesNotThrowAnyException();
        assertThat(payment).isEqualTo(updatedPayment);

        verify(paymentRepository).findById(paymentIntentId);
        verify(paymentRepository).save(updatedPayment);
        verifyNoInteractions(paymentContextService, rideServiceApiClient);
    }

    @Test
    void updatePaymentStatus_ShouldThrowResourceNotFoundException_WhenPaymentNotFound() {
        String paymentIntentId = StripeTestUtil.NOT_EXISTING_INTENT_ID;
        PaymentStatus paymentStatus = PaymentStatus.SUCCEEDED;

        when(paymentRepository.findById(paymentIntentId)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> paymentService.updatePaymentStatus(paymentIntentId, paymentStatus))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(ApplicationMessages.PAYMENT_WITH_ID_NOT_FOUND);

        verify(paymentRepository).findById(paymentIntentId);
        verifyNoMoreInteractions(paymentRepository);
        verifyNoInteractions(paymentContextService, rideServiceApiClient);
    }

    @Test
    void updatePaymentStatus_ShouldChangeRidePaymentStatus_WhenPaymentExistsAndStatusSucceed() {
        String paymentIntentId = StripeTestUtil.INTENT_ID;
        PaymentStatus paymentStatus = PaymentStatus.SUCCEEDED;
        Payment payment = PaymentTestUtil.getPaymentBuilder().build();
        PaymentContext paymentContext = PaymentContextTestUtil.getPaymentContextBuilder().build();
        ObjectId rideId = new ObjectId(paymentContext.getContextId());

        when(paymentRepository.findById(paymentIntentId))
                .thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(any(Payment.class));
        when(paymentContextService.getPaymentContext(payment))
                .thenReturn(paymentContext);
        when(rideServiceApiClient.setRidePaymentStatus(rideId, true))
                .thenReturn(ResponseEntity.ok().build());

        assertThatCode(
                () -> paymentService.updatePaymentStatus(paymentIntentId, paymentStatus))
                .doesNotThrowAnyException();

        verify(paymentRepository).findById(paymentIntentId);
        verify(paymentRepository).save(any(Payment.class));
        verify(paymentContextService).getPaymentContext(any(Payment.class));
        verify(rideServiceApiClient).setRidePaymentStatus(rideId, true);
    }
}
