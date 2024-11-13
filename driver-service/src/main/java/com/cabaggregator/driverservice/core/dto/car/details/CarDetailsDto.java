package com.cabaggregator.driverservice.core.dto.car.details;

import java.time.LocalDate;

public record CarDetailsDto(
        LocalDate releaseDate,
        Integer seatCapacity
) {
}
