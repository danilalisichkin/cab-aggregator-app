package com.cabaggregator.driverservice.core.dto.driver;

public record DriverDto(
        Long id,
        String phoneNumber,
        String email,
        String firstName,
        String lastName,
        Double rating,
        Long carId
) {
}
