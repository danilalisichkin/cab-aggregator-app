package com.cabaggregator.rideservice.core.dto.promo;

import java.time.LocalDateTime;

public record PromoCodeAddingDto(
        String value,
        Double discount,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
