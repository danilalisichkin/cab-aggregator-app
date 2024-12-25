package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;

public interface CarDetailsService {
    CarDetailsDto updateCarDetails(Long carId, CarDetailsSettingDto carDetailsDto);
}
