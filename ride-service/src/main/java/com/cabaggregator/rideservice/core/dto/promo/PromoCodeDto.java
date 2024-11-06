package com.cabaggregator.rideservice.core.dto.promo;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public record PromoCodeDto(
        ObjectId id,
        String value,
        Double discount,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
