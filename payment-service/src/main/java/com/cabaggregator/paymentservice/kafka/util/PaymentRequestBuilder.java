package com.cabaggregator.paymentservice.kafka.util;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentRequest;
import com.cabaggregator.paymentservice.kafka.dto.PaymentDefaultRequest;
import org.springframework.stereotype.Component;

@Component
public class PaymentRequestBuilder {
    public PaymentRequest buildFromDefaultRequestAndPaymentMethod(
            PaymentDefaultRequest defaultRequest, String paymentMethodId) {

        return new PaymentRequest(
                defaultRequest.paymentAccountId(),
                defaultRequest.unitAmount(),
                paymentMethodId,
                defaultRequest.context(),
                defaultRequest.contextId());
    }
}
