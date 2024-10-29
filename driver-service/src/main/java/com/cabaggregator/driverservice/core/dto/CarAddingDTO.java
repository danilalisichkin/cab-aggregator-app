package com.cabaggregator.driverservice.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarAddingDTO extends CarDTO {
    String licensePlate;
    String make;
    String model;
    String color;
}
