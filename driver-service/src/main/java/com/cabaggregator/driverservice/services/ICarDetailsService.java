package com.cabaggregator.driverservice.services;

import com.cabaggregator.driverservice.core.dto.CarDetailsAddingDTO;
import com.cabaggregator.driverservice.core.dto.CarDetailsDTO;

import java.util.List;

public interface ICarDetailsService {
    List<CarDetailsDTO> getAllCarDetails();

    CarDetailsDTO getCarDetailsById(long id);

    CarDetailsDTO getCarDetailsByCarId(long carId);

    CarDetailsDTO saveCarDetails(CarDetailsAddingDTO carDetailsDTO);

    CarDetailsDTO updateCarDetails(CarDetailsDTO carDetailsDTO);
}
