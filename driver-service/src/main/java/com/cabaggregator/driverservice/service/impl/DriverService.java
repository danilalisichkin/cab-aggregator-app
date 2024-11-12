package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.service.IDriverService;
import org.springframework.stereotype.Service;

@Service
public class DriverService implements IDriverService {
    @Override
    public PagedDto<DriverDto> getPageOfDrivers(int pageNumber, int pageSize, String sortField, String sortOrder) {
        return null;
    }

    @Override
    public DriverDto getDriverById(Long id) {
        return null;
    }

    @Override
    public DriverDto saveDriver(DriverAddingDto driverDto) {
        return null;
    }

    @Override
    public DriverDto updateDriver(Long id, DriverUpdatingDto driverDto) {
        return null;
    }

    @Override
    public void deleteDriverById(Long id) {

    }
}
