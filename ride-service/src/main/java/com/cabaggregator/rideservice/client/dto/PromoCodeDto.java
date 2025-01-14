package com.cabaggregator.rideservice.client.dto;

import java.time.LocalDate;

public record PromoCodeDto(
        String value,
        Integer discountPercentage,
        LocalDate endDate,
        Long limit
) {
}