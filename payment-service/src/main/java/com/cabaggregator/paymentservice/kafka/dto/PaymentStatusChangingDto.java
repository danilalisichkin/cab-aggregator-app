package com.cabaggregator.paymentservice.kafka.dto;

import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;

public record PaymentStatusChangingDto(
        String contextId,
        PaymentStatus paymentStatus
) {
}
