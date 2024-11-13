package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PageRequestDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;

public interface CarService {
    PagedDto<CarDto> getPageOfCars(PageRequestDto pageRequestDto);

    CarDto getCarById(Long id);

    CarFullDto getFullCarById(Long id);

    CarDto saveCar(CarAddingDto carDto);

    CarDto updateCar(Long id, CarUpdatingDto carDto);

    CarFullDto updateCarDetails(Long carId, CarDetailsSettingDto carDetailsDto);

    void deleteCarById(Long id);
}
