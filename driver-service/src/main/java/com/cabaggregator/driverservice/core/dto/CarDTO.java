package com.cabaggregator.driverservice.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDTO {
    private long id;
    private String licensePlate;
    private String make;
    private String model;
    private String color;
}
