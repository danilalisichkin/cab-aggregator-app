package com.cabaggregator.driverservice.core.dto;

import java.time.LocalDate;

public class CarDetailsAddingDTO extends CarDetailsDTO {
    public CarDetailsAddingDTO(LocalDate releaseDate, int seatCapacity, long carId) {
        super(releaseDate, seatCapacity, carId);
    }
}
