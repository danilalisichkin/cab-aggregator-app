package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.constant.ApplicationMessages;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSortField;
import com.cabaggregator.driverservice.core.mapper.DriverMapper;
import com.cabaggregator.driverservice.core.mapper.PageMapper;
import com.cabaggregator.driverservice.entity.Car;
import com.cabaggregator.driverservice.entity.Driver;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.repository.DriverRepository;
import com.cabaggregator.driverservice.service.DriverService;
import com.cabaggregator.driverservice.util.PageRequestBuilder;
import com.cabaggregator.driverservice.validator.DriverValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;

    private final CarRepository carRepository;

    private final DriverMapper driverMapper;

    private final PageMapper pageMapper;

    private final DriverValidator driverValidator;

    @Override
    public PageDto<DriverDto> getPageOfDrivers(
            Integer offset, Integer limit, DriverSortField sortField, Sort.Direction sortOrder) {

        PageRequest request = PageRequestBuilder.buildPageRequest(offset, limit, sortField.getValue(), sortOrder);

        return pageMapper.pageToPageDto(
                driverMapper.entityPageToDtoPage(
                        driverRepository.findAll(request)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    public DriverDto getDriverById(UUID id) {
        return driverMapper.entityToDto(
                getDriverEntityById(id));
    }

    @Override
    @Transactional
    public DriverDto saveDriver(DriverAddingDto driverDto) {
        driverValidator.validateIdUniqueness(driverDto.id());
        driverValidator.validatePhoneUniqueness(driverDto.phoneNumber());
        driverValidator.validateEmailUniqueness(driverDto.email());

        Driver driver = driverMapper.dtoToEntity(driverDto);

        return driverMapper.entityToDto(
                driverRepository.save(driver));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    public DriverDto updateDriver(UUID id, DriverUpdatingDto driverDto) {
        Driver driverToUpdate = getDriverEntityById(id);

        if (!driverToUpdate.getPhoneNumber().equals(driverDto.phoneNumber())) {
            driverValidator.validatePhoneUniqueness(driverDto.phoneNumber());
        }
        if (!driverToUpdate.getEmail().equals(driverDto.email())) {
            driverValidator.validateEmailUniqueness(driverDto.email());
        }

        driverMapper.updateEntityFromDto(driverDto, driverToUpdate);

        return driverMapper.entityToDto(
                driverRepository.save(driverToUpdate));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    public DriverDto updateDriverCarId(UUID id, Long carId) {
        Driver driverToUpdate = getDriverEntityById(id);

        driverValidator.validateDriverCarUniqueness(carId);

        driverToUpdate.setCar(getCarEntityById(carId));

        return driverMapper.entityToDto(
                driverRepository.save(driverToUpdate));
    }

    @Override
    public void deleteDriverById(UUID id) {
        driverValidator.validateExistenceOfDriverWithId(id);

        driverRepository.deleteById(id);
    }

    private Driver getDriverEntityById(UUID id) {
        return driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.DRIVER_WITH_ID_NOT_FOUND,
                        id));
    }

    private Car getCarEntityById(Long id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.CAR_WITH_ID_NOT_FOUND,
                        id));
    }
}
