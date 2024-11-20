package com.cabaggregator.driverservice.core.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with stored driver data")
public record DriverDto(
        String id,
        String phoneNumber,
        String email,
        String firstName,
        String lastName,
        Double rating,
        Long carId
) {
}
