package com.cabaggregator.driverservice.services;

import com.cabaggregator.driverservice.core.dto.CarAddingDTO;
import com.cabaggregator.driverservice.core.dto.CarDTO;

import java.util.List;

public interface ICarService {
    List<CarDTO> getAllCars();

    CarDTO getCarById(long id);

    CarDTO getCarByLicensePlate(String licensePlate);

    CarDTO saveCar(CarAddingDTO carDTO);

    CarDTO updateCar(CarDTO carDTO);

    void deleteCarById(long id);

    void deleteAllCars();
}
