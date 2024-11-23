package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.CarSortField;
import org.springframework.data.domain.Sort;

public interface CarService {
    PageDto<CarDto> getPageOfCars(
            Integer offset, Integer limit, CarSortField sortField, Sort.Direction sortOrder);

    CarDto getCarById(Long id);

    CarFullDto getFullCarById(Long id);

    CarDto saveCar(CarAddingDto carDto);

    CarDto updateCar(Long id, CarUpdatingDto carDto);

    CarFullDto updateCarDetails(Long carId, CarDetailsSettingDto carDetailsDto);

    void deleteCarById(Long id);
}
