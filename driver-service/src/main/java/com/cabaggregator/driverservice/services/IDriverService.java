package com.cabaggregator.driverservice.services;

import com.cabaggregator.driverservice.core.dto.CarAddingDTO;
import com.cabaggregator.driverservice.core.dto.DriverDTO;

import java.util.List;

public interface IDriverService {
    List<DriverDTO> getAllDrivers();

    DriverDTO getDriverById(long id);

    DriverDTO getDriverByPhone(String phone);

    DriverDTO getDriverByEmail(String email);

    DriverDTO saveDriver(CarAddingDTO carDTO);

    DriverDTO updateDriver(DriverDTO carDTO);

    void deleteDriverById(long id);

    void deleteAllDrivers();
}
