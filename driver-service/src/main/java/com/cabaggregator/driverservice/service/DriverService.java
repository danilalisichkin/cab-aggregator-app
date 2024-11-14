package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PageRequestDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;

public interface DriverService {
    PagedDto<DriverDto> getPageOfDrivers(PageRequestDto pageRequestDto);

    DriverDto getDriverById(Long id);

    DriverDto saveDriver(DriverAddingDto driverDto);

    DriverDto updateDriver(Long id, DriverUpdatingDto driverDto);

    void deleteDriverById(Long id);
}
