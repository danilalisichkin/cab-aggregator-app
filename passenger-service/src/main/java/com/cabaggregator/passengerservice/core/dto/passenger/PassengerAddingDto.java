package com.cabaggregator.passengerservice.core.dto.passenger;

import com.cabaggregator.passengerservice.core.constant.ValidationErrors;
import com.cabaggregator.passengerservice.core.constant.ValidationRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Entry to add new passenger")
public record PassengerAddingDto(
        @NotNull
        UUID id,

        @NotEmpty
        @Pattern(regexp = ValidationRegex.PHONE_BELARUS_FORMAT,
                message = ValidationErrors.INVALID_PHONE_FORMAT)
        String phoneNumber,

        @NotEmpty
        @Email
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String email,

        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String firstName,

        @NotEmpty
        @Size(max = 50, message = ValidationErrors.INVALID_STRING_MAX_LENGTH)
        String lastName
) {
}
