package com.cabaggregator.driverservice.core.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with stored car data (without its details)")
public record CarDto(
        Long id,
        String licensePlate,
        String make,
        String model,
        String color
) {
}
