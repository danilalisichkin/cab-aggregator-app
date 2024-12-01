package com.cabaggregator.paymentservice.core.dto.payment.account;

import java.util.UUID;

public record PaymentAccountAddingDto(
        UUID userId,
        String phoneNumber,
        String email,
        String firstName,
        String lastName
) {
}