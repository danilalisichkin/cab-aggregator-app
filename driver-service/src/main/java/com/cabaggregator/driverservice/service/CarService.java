package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.core.enums.sort.CarSort;
import com.cabaggregator.driverservice.entity.Car;

public interface CarService {
    PagedDto<CarDto> getPageOfCars(Integer offset, Integer limit, CarSort sort);

    CarDto getCarById(Long id);

    Car getCarEntityById(Long id);

    CarFullDto getFullCarById(Long id);

    CarDto saveCar(CarAddingDto carDto);

    CarDto updateCar(Long id, CarUpdatingDto carDto);

    CarFullDto updateCarDetails(Long carId, CarDetailsSettingDto carDetailsDto);

    void deleteCarById(Long id);
}
