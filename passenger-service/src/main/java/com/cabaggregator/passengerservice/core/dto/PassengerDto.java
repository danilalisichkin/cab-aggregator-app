package com.cabaggregator.passengerservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with stored passenger data")
public record PassengerDto(
        long id,
        String phoneNumber,
        String email,
        String firstName,
        String lastName,
        double rating
) {
}
