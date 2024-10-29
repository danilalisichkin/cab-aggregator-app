package com.cabaggregator.driverservice.core.dto;

import java.time.LocalDate;

public class CarDetailsAddingDTO extends CarDetailsDTO {
    public CarDetailsAddingDTO(LocalDate releaseDate, int seatCapacity, String carLicensePlate) {
        super(releaseDate, seatCapacity, carLicensePlate);
    }
}
