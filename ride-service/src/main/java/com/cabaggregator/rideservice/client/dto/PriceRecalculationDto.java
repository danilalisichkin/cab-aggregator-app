package com.cabaggregator.rideservice.client.dto;

import java.util.UUID;

public record PriceRecalculationDto(
        UUID passengerId,
        String promoCode,
        Long price
) {
}
