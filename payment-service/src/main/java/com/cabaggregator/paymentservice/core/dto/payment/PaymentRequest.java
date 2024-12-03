package com.cabaggregator.paymentservice.core.dto.payment;

import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;

import java.util.UUID;

public record PaymentRequest(
        UUID paymentAccountId,
        Long unitAmount,
        String paymentMethodId,
        PaymentContextType context,
        String contextId
) {
}
