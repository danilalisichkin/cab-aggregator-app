package com.cabaggregator.driverservice.core.dto.driver;

public record DriverUpdatingDto(
        String phoneNumber,
        String email,
        String firstName,
        String lastName,
        Double rating
) {
}
