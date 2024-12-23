package com.cabaggregator.pricecalculationservice.core.dto;

import com.cabaggregator.pricecalculationservice.core.constant.ValidationErrors;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.bson.types.ObjectId;

import java.util.List;

@Schema(description = "Entry to with required data to calculate ride price")
public record PriceCalculationRequest(
        @NotNull
        ObjectId rideId,

        @NotNull
        @Size(min = 1, max = 2, message = ValidationErrors.INVALID_COLLECTION_SIZE)
        List<Double> pickUpCoordinates,

        @NotNull
        @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
        Long distance,

        @NotNull
        @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
        Long duration,

        @NotEmpty(message = ValidationErrors.STRING_IS_EMPTY)
        String fare
) {
}
