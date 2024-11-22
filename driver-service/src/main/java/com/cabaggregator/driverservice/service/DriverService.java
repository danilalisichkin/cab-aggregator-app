package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSort;

import java.util.UUID;

public interface DriverService {
    PagedDto<DriverDto> getPageOfDrivers(Integer offset, Integer limit, DriverSort sort);

    DriverDto getDriverById(UUID id);

    DriverDto saveDriver(DriverAddingDto driverDto);

    DriverDto updateDriver(UUID id, DriverUpdatingDto driverDto);

    DriverDto updateDriverRating(UUID id, Double rating);

    void deleteDriverById(UUID id);
}
