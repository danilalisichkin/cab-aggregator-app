package com.cabaggregator.rideservice.core.dto.price;

import java.util.UUID;

public record PriceRecalculationDto(
        UUID passengerId,
        String promoCode,
        Long price
) {
}
