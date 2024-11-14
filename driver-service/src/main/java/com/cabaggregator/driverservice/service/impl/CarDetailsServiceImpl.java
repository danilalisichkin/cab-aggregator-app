package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.repository.CarDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarDetailsServiceImpl implements com.cabaggregator.driverservice.service.CarDetailsService {
    private final CarDetailsRepository carDetailsRepository;

    @Override
    public CarDetailsDto getCarDetailsByCarId(Long carId) {
        return null;
    }

    @Override
    public CarDetailsDto saveCarDetails(Long carId, CarDetailsSettingDto carDetailsDto) {
        return null;
    }
}
