package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.entity.CarDetails;

public interface CarDetailsService {
    CarDetails saveCarDetails(Long carId, CarDetailsSettingDto carDetailsDto);
}
