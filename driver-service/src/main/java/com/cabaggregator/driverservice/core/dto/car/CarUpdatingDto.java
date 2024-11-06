package com.cabaggregator.driverservice.core.dto.car;

public record CarUpdatingDto(
        String licensePlate,
        String make,
        String model,
        String color
) {
}
