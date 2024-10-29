package com.cabaggregator.driverservice.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverAddingDTO {
    private String phoneNumber;
    private String email;
    private String name;
    private long carId;
}
