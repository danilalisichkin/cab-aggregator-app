package com.cabaggregator.rideservice.core.dto;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record Address(
        @NotEmpty
        @Size(min = 4, max = 100, message = ValidationErrors.INVALID_STRING_LENGTH)
        String fullAddress,

        @NotNull
        @NotEmpty
        @Size(min = 2, max = 2, message = ValidationErrors.INVALID_COLLECTION_SIZE)
        List<Double> coordinates
) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Address)) return false;
        Address other = (Address) obj;
        return coordinates.equals(other.coordinates);
    }
}
