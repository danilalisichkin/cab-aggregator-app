package com.cabaggregator.paymentservice.service;

import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;

public interface PaymentContextService {
    void savePaymentContext(Payment payment, PaymentContextType type, String contextId);

    PaymentContext getPaymentContext(Payment payment);
}
