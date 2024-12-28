package com.cabaggregator.paymentservice.core.dto.payment;

import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;
import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentDto(
        String paymentIntentId,
        UUID paymentAccountId,
        PaymentStatus status,
        LocalDateTime createdAt,
        PaymentContextType contextType,
        String contextId
) {
}
