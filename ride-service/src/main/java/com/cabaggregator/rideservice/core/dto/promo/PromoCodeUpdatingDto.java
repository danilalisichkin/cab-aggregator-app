package com.cabaggregator.rideservice.core.dto.promo;

import java.time.LocalDateTime;

public record PromoCodeUpdatingDto(
        Integer discount,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
