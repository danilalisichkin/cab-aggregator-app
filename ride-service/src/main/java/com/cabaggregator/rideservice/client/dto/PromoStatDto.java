package com.cabaggregator.rideservice.client.dto;

import java.util.UUID;

public record PromoStatDto(
        Long id,
        UUID userId,
        String promoCode
) {
}
