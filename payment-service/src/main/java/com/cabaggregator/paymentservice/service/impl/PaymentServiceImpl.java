package com.cabaggregator.paymentservice.service.impl;

import com.cabaggregator.paymentservice.client.RideServiceApiClient;
import com.cabaggregator.paymentservice.core.constant.ApplicationMessages;
import com.cabaggregator.paymentservice.core.constant.StringTemplates;
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
import com.cabaggregator.paymentservice.service.PaymentService;
import com.cabaggregator.paymentservice.service.StripeService;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final StripeService stripeService;

    private final PaymentContextService paymentContextService;

    private final RideServiceApiClient rideServiceApiClient;

    private final PaymentAccountRepository paymentAccountRepository;

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        PaymentIntent paymentIntent = createPaymentForRide(paymentRequest);

        return paymentMapper.intentToResponse(paymentIntent);
    }

    @Override
    @Transactional
    public void updatePaymentStatus(String paymentIntentId, PaymentStatus paymentStatus) {
        Payment paymentToUpdate = getPaymentEntity(paymentIntentId);

        paymentToUpdate.setStatus(paymentStatus);
        paymentRepository.save(paymentToUpdate);

        if (paymentStatus.equals(PaymentStatus.SUCCEEDED)) {
            endPayment(paymentToUpdate);
        }
    }

    private void endPayment(Payment payment) {
        PaymentContext paymentContext = paymentContextService.getPaymentContext(payment);

        ObjectId rideId = new ObjectId(paymentContext.getContextId());

        rideServiceApiClient.setRidePaymentStatus(rideId, true);
    }

    private PaymentIntent createPaymentForRide(PaymentRequest paymentRequest) {
        PaymentAccount paymentAccount = getPaymentAccountEntity(paymentRequest.paymentAccountId());
        Customer stripeCustomer = getStripeCustomerFromPaymentAccount(paymentAccount);

        String paymentDescription = String.format(StringTemplates.RIDE_PAYMENT_DESCRIPTION, paymentRequest.contextId());
        PaymentIntent sendedPaymentIntent = sendPaymentIntent(stripeCustomer, paymentRequest, paymentDescription);

        Payment savedPayment = savePayment(paymentAccount, sendedPaymentIntent);
        paymentContextService.savePaymentContext(savedPayment, PaymentContextType.RIDE, paymentRequest.contextId());

        return sendedPaymentIntent;
    }

    private Payment savePayment(PaymentAccount paymentAccount, PaymentIntent paymentIntent) {
        Payment newPayment = Payment.builder()
                .paymentAccount(paymentAccount)
                .paymentIntentId(paymentIntent.getId())
                .status(PaymentStatus.PROCESSING)
                .build();

        return paymentRepository.save(newPayment);
    }

    private PaymentIntent sendPaymentIntent(
            Customer stripeCustomer, PaymentRequest paymentRequest, String paymentDescription) {

        return stripeService.createPaymentIntent(
                stripeCustomer,
                paymentDescription,
                paymentRequest.unitAmount(),
                paymentRequest.paymentMethodId());
    }

    private Payment getPaymentEntity(String id) {
        return paymentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PAYMENT_WITH_ID_NOT_FOUND,
                        id));
    }

    private PaymentAccount getPaymentAccountEntity(UUID id) {
        return paymentAccountRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.ACCOUNT_WITH_ID_NOT_FOUND,
                        id));
    }

    private Customer getStripeCustomerFromPaymentAccount(PaymentAccount paymentAccount) {
        return stripeService.retrieveCustomer(paymentAccount.getStripeCustomerId());
    }
}
