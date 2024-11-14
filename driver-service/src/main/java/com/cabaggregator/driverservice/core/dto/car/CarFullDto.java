package com.cabaggregator.driverservice.core.dto.car;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;

public record CarFullDto(
        CarDto car,
        CarDetailsDto details
) {
}
