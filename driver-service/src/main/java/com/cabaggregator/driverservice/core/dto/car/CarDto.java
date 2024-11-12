package com.cabaggregator.driverservice.core.dto.car;

public record CarDto(
        Long id,
        String licensePlate,
        String make,
        String model,
        String color
) {
}
