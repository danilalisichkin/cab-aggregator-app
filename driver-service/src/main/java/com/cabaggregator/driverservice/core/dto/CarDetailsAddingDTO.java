package com.cabaggregator.driverservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Entry to add new car details")
public class CarDetailsAddingDTO extends CarDetailsDTO {
    public CarDetailsAddingDTO(LocalDate releaseDate, int seatCapacity, long carId) {
        super(releaseDate, seatCapacity, carId);
    }
}
