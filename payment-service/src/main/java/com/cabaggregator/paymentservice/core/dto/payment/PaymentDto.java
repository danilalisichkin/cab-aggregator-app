package com.cabaggregator.paymentservice.core.dto.payment;

import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;
import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Server response with stored payment data")
public record PaymentDto(
        String paymentIntentId,
        UUID paymentAccountId,
        PaymentStatus status,
        LocalDateTime createdAt,
        PaymentContextType contextType,
        String contextId
) {
}
