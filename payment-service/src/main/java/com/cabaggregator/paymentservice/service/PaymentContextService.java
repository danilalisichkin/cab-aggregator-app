package com.cabaggregator.paymentservice.service;

import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;

import java.util.List;

public interface PaymentContextService {
    List<PaymentContext> getPaymentContextsByTypeAndContextId(PaymentContextType type, String contextId);

    void savePaymentContext(Payment payment, PaymentContextType type, String contextId);

    PaymentContext getPaymentContext(Payment payment);
}
