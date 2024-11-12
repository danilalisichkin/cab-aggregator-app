package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.service.ICarDetailsService;
import com.cabaggregator.driverservice.service.ICarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarDetailsService implements ICarDetailsService {
    private final ICarService carService;

    @Override
    public PagedDto<CarDetailsDto> getPageOfCarDetails(int pageNumber, int pageSize, String sortField, String sortOrder) {
        return null;
    }

    @Override
    public CarDetailsDto getCarDetailsByCarId(Long carId) {
        return null;
    }

    @Override
    public CarDetailsDto saveCarDetails(Long carId, CarDetailsSettingDto carDetailsDto) {
        return null;
    }

    @Override
    public CarDetailsDto updateCarDetails(Long carId, CarDetailsSettingDto carDetailsDto) {
        return null;
    }
}
