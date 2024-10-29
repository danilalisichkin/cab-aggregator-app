package com.cabaggregator.driverservice.core.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CarDetailsDTO {
    private LocalDate releaseDate;
    private int seatCapacity;
    private long carId;
}
