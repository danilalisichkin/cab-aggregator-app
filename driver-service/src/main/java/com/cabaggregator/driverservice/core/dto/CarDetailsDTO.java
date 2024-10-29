package com.cabaggregator.driverservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Server response with stored car details data, entry to update existing car details")
public class CarDetailsDTO {
    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private int seatCapacity;

    @NotNull
    private long carId;
}
