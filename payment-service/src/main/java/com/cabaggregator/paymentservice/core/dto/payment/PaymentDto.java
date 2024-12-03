package com.cabaggregator.paymentservice.core.dto.payment;

import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;
import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentDto(
        String paymentIntentId,
        String paymentAccountId,
        PaymentStatus status,
        LocalDateTime createdAt,
        PaymentContextType contextType,
        String contextId
) {
}
