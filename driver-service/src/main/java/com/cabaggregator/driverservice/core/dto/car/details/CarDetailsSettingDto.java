package com.cabaggregator.driverservice.core.dto.car.details;

import java.time.LocalDate;

public record CarDetailsSettingDto(
        LocalDate releaseDate,
        Integer seatCapacity
) {
}
