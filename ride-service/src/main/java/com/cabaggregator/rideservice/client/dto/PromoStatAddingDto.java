package com.cabaggregator.rideservice.client.dto;

import java.util.UUID;

public record PromoStatAddingDto(
        UUID userId,
        String promoCode
) {
}