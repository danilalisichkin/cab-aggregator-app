package com.cabaggregator.paymentservice.service.impl;

import com.cabaggregator.paymentservice.core.constant.ApplicationMessages;
import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;
import com.cabaggregator.paymentservice.exception.ResourceNotFoundException;
import com.cabaggregator.paymentservice.repository.PaymentContextRepository;
import com.cabaggregator.paymentservice.service.PaymentContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentContextServiceImpl implements PaymentContextService {
    private final PaymentContextRepository paymentContextRepository;

    @Override
    public List<PaymentContext> getPaymentContextsByTypeAndContextId(PaymentContextType type, String contextId) {
        return paymentContextRepository.findAllByTypeAndContextId(type, contextId);
    }

    @Override
    public void savePaymentContext(Payment payment, PaymentContextType type, String contextId) {
        PaymentContext newPaymentContext = PaymentContext.builder()
                .payment(payment)
                .type(type)
                .contextId(contextId)
                .build();

        paymentContextRepository.save(newPaymentContext);
    }

    @Override
    public PaymentContext getPaymentContext(Payment payment) {
        return paymentContextRepository
                .findByPayment(payment)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.CONTEXT_FOR_PAYMENT_WITH_ID_NOT_FOUND,
                        payment.getPaymentIntentId()));
    }
}
