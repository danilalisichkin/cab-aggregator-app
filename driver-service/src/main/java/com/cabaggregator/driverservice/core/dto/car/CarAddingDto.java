package com.cabaggregator.driverservice.core.dto.car;

public record CarAddingDto(
        String licensePlate,
        String make,
        String model,
        String color
) {
}
