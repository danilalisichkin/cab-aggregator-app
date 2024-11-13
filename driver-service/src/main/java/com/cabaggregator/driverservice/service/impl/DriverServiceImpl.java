package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PageRequestDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements com.cabaggregator.driverservice.service.DriverService {
    private final DriverRepository driverRepository;

    @Override
    public PagedDto<DriverDto> getPageOfDrivers(PageRequestDto pageRequestDto) {
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
