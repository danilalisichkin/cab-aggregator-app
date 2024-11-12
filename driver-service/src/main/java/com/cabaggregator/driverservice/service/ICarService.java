package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;

public interface ICarService {
    PagedDto<CarDto> getPageOfCars(int pageNumber, int pageSize, String sortField, String sortOrder);

    CarDto getCarById(Long id);

    CarDto saveCar(CarAddingDto carDto);

    CarDto updateCar(Long id, CarUpdatingDto carDto);

    void deleteCarById(Long id);
}
