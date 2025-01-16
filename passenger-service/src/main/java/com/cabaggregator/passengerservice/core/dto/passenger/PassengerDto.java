package com.cabaggregator.passengerservice.core.dto.passenger;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Server response with stored passenger data")
public record PassengerDto(
        UUID id,
        String phoneNumber,
        String email,
        String firstName,
        String lastName
) {
}
