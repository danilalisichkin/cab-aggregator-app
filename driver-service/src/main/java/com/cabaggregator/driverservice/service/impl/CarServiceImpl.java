package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PageRequestDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.service.CarDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements com.cabaggregator.driverservice.service.CarService {
    private final CarRepository carRepository;
    private final CarDetailsService carDetailsService;

    @Override
    public PagedDto<CarDto> getPageOfCars(PageRequestDto pageRequestDto) {
        return null;
    }

    @Override
    public CarDto getCarById(Long id) {
        return null;
    }

    @Override
    public CarFullDto getFullCarById(Long id) {
        return null;
    }

    @Override
    public CarDto saveCar(CarAddingDto carDto) {
        return null;
    }

    @Override
    public CarDto updateCar(Long id, CarUpdatingDto carDto) {
        return null;
    }

    @Override
    public CarFullDto updateCarDetails(Long carId, CarDetailsSettingDto carDetailsDto) {
        return null;
    }

    @Override
    public void deleteCarById(Long id) {

    }
}
