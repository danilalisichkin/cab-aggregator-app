package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSortField;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface DriverService {
    PageDto<DriverDto> getPageOfDrivers(
            Integer offset, Integer limit, DriverSortField sortField, Sort.Direction sortOrder);

    DriverDto getDriverById(UUID id);

    DriverDto saveDriver(DriverAddingDto driverDto);

    DriverDto updateDriver(UUID id, DriverUpdatingDto driverDto);

    DriverDto updateDriverCarId(UUID id, Long carId);

    void deleteDriverById(UUID id);
}
