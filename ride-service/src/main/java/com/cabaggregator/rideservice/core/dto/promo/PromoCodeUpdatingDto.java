package com.cabaggregator.rideservice.core.dto.promo;

import java.time.LocalDateTime;

public record PromoCodeUpdatingDto(
        String value,
        double discount,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
