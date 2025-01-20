package com.cabaggregator.rideservice.kafka.dto;

import com.cabaggregator.rideservice.kafka.enums.PaymentStatus;

public record PaymentStatusChangingDto(
        String contextId,
        PaymentStatus paymentStatus
) {
}
