package com.cabaggregator.driverservice.core.dto.car;

public record CarDto(
        long id,
        String licensePlate,
        String make,
        String model,
        String color
) {
}
