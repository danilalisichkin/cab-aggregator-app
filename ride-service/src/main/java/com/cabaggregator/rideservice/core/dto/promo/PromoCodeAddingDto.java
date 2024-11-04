package com.cabaggregator.rideservice.core.dto.promo;

import java.time.LocalDateTime;

public record PromoCodeAddingDto(
        String value,
        double discount,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
