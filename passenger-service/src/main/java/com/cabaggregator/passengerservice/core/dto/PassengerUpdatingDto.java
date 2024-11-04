package com.cabaggregator.passengerservice.core.dto;

import com.cabaggregator.passengerservice.core.constant.MessageKeys;
import com.cabaggregator.passengerservice.core.constant.ValidationRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(description = "Entry to update existing passenger")
public record PassengerUpdatingDto(
        @NotNull
        @Pattern(regexp = ValidationRegex.PHONE_BELARUS_FORMAT,
                message = MessageKeys.VALIDATION_INVALID_PHONE_FORMAT)
        String phoneNumber,

        @NotNull
        @Email
        @Size(max = 50, message = MessageKeys.VALIDATION_INVALID_LENGTH)
        String email,

        @NotNull
        @Size(max = 50, message = MessageKeys.VALIDATION_INVALID_LENGTH)
        String firstName,

        @NotNull
        @Size(max = 50, message = MessageKeys.VALIDATION_INVALID_LENGTH)
        String lastName,

        @NotNull
        @PositiveOrZero
        double rating
) {
}
