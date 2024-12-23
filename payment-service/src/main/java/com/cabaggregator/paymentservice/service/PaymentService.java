package com.cabaggregator.paymentservice.service;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentRequest;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest paymentRequest);

    void updatePaymentStatus(String paymentIntentId, PaymentStatus paymentStatus);
}
