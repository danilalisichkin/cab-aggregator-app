package com.cabaggregator.driverservice.core.dto.driver;

public record DriverAddingDto(
        String phoneNumber,
        String email,
        String name,
        long carId
) {
}
