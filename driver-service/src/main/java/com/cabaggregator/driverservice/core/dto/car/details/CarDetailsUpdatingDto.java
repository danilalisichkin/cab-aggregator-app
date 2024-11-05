package com.cabaggregator.driverservice.core.dto.car.details;

import java.time.LocalDate;

public record CarDetailsUpdatingDto(
        LocalDate releaseDate,
        int seatCapacity,
        long carId
) {
}
