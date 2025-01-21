package com.cabaggregator.rideservice.kafka.dto;

import com.cabaggregator.rideservice.kafka.enums.PaymentContextType;

import java.util.UUID;

public record PaymentDefaultRequest(
        UUID paymentAccountId,
        Long unitAmount,
        PaymentContextType context,
        String contextId
) {
}
