package com.cabaggregator.driverservice.core.dto.driver;

public record DriverDto(
        long id,
        String phoneNumber,
        String email,
        String name,
        double rating,
        long carId
) {
}
