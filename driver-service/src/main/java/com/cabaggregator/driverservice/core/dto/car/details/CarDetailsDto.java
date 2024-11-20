package com.cabaggregator.driverservice.core.dto.car.details;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Server response with stored car details data")
public record CarDetailsDto(
        LocalDate releaseDate,
        Integer seatCapacity
) {
}
