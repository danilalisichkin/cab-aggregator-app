package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSort;

public interface DriverService {
    PagedDto<DriverDto> getPageOfDrivers(Integer offset, Integer limit, DriverSort sort);

    DriverDto getDriverById(String id);

    DriverDto saveDriver(DriverAddingDto driverDto);

    DriverDto updateDriver(String id, DriverUpdatingDto driverDto);

    DriverDto updateDriverRating(String id, Double rating);

    void deleteDriverById(String id);
}
