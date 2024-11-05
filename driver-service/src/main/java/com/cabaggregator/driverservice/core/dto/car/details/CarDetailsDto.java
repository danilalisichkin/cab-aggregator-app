package com.cabaggregator.driverservice.core.dto.car.details;

import java.time.LocalDate;

public record CarDetailsDto(
        long id,
        LocalDate releaseDate,
        int seatCapacity,
        long carId
) {
}
