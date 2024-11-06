package com.cabaggregator.rideservice.core.dto.promo;

import java.time.LocalDateTime;

public record PromoCodeUpdatingDto(
        Double discount,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
