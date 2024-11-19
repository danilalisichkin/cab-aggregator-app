package com.cabaggregator.driverservice.core.dto.car;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with stored car full data (with its details)")
public record CarFullDto(
        CarDto car,
        CarDetailsDto details
) {
}
