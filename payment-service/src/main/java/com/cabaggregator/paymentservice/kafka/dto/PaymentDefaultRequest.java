package com.cabaggregator.paymentservice.kafka.dto;

import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;

import java.util.UUID;

public record PaymentDefaultRequest(
        UUID paymentAccountId,
        Long unitAmount,
        PaymentContextType context,
        String contextId
) {
}
