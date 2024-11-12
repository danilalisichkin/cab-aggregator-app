package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.service.ICarService;
import org.springframework.stereotype.Service;

@Service
public class CarService implements ICarService {
    @Override
    public PagedDto<CarDto> getPageOfCars(int pageNumber, int pageSize, String sortField, String sortOrder) {
        return null;
    }

    @Override
    public CarDto getCarById(Long id) {
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
    public void deleteCarById(Long id) {

    }
}
