package com.cabaggregator.driverservice.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarAddingDTO {
    String licensePlate;
    String make;
    String model;
    String color;
}
