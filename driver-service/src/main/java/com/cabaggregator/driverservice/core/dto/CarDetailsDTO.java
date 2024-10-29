package com.cabaggregator.driverservice.core.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CarDetailsDTO {
    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private int seatCapacity;

    @NotNull
    private long carId;
}
