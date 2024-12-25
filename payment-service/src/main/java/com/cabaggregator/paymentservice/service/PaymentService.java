package com.cabaggregator.paymentservice.service;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentDto;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentRequest;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import org.bson.types.ObjectId;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> getRidePayments(ObjectId rideId);

    PaymentResponse createPayment(PaymentRequest paymentRequest);

    void updatePaymentStatus(String paymentIntentId, PaymentStatus paymentStatus);
}
