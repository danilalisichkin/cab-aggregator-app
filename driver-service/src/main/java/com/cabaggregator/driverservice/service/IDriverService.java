package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;

public interface IDriverService {
    PagedDto<DriverDto> getPageOfDrivers(int pageNumber, int pageSize, String sortField, String sortOrder);

    DriverDto getDriverById(Long id);

    DriverDto saveDriver(DriverAddingDto driverDto);

    DriverDto updateDriver(Long id, DriverUpdatingDto driverDto);

    void deleteDriverById(Long id);
}
