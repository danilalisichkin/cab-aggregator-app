package com.cabaggregator.driverservice.core.dto.car.details;

import java.time.LocalDate;

public record CarDetailsAddingDto(
        LocalDate releaseDate,
        int seatCapacity,
        long carId
) {
}
