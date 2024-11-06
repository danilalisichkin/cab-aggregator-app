package com.cabaggregator.driverservice.core.dto.car.details;

import java.time.LocalDate;

public record CarDetailsDto(
        Long id,
        LocalDate releaseDate,
        Integer seatCapacity
) {
}
