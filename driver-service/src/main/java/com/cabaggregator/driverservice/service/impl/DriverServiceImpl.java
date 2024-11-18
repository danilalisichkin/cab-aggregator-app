package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.constant.ApplicationMessages;
import com.cabaggregator.driverservice.core.constant.DefaultValues;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSort;
import com.cabaggregator.driverservice.core.mapper.DriverMapper;
import com.cabaggregator.driverservice.core.mapper.PageMapper;
import com.cabaggregator.driverservice.entity.Driver;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.DriverRepository;
import com.cabaggregator.driverservice.service.CarService;
import com.cabaggregator.driverservice.util.PageRequestBuilder;
import com.cabaggregator.driverservice.validator.DriverValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements com.cabaggregator.driverservice.service.DriverService {
    private final DriverRepository driverRepository;

    private final CarService carService;

    private final DriverMapper driverMapper;
    private final PageMapper pageMapper;

    private final DriverValidator driverValidator;

    @Override
    public PagedDto<DriverDto> getPageOfDrivers(Integer offset, Integer limit, DriverSort sort) {
        PageRequest request = PageRequestBuilder.buildPageRequest(offset, limit, sort.getSortValue());

        return pageMapper.pageToPagedDto(
                driverMapper.entityPageToDtoPage(
                        driverRepository.findAll(request)));
    }

    @Override
    public DriverDto getDriverById(String id) {
        return driverMapper.entityToDto(
                getDriverEntityById(id));
    }

    @Override
    @Transactional
    public DriverDto saveDriver(DriverAddingDto driverDto) {
        driverValidator.validateIdUniqueness(driverDto.id());
        driverValidator.validatePhoneUniqueness(driverDto.phoneNumber());
        driverValidator.validateEmailUniqueness(driverDto.email());
        driverValidator.validateDriverCarUniqueness(driverDto.carId());

        Driver driver = driverMapper.dtoToEntity(driverDto);
        driver.setRating(DefaultValues.DRIVER_RATING);
        driver.setCar(
                carService.getCarEntityById(driverDto.carId()));

        return driverMapper.entityToDto(
                driverRepository.save(driver));
    }

    @Override
    @Transactional
    public DriverDto updateDriver(String id, DriverUpdatingDto driverDto) {
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
    public DriverDto updateDriverRating(String id, Double rating) {
        Driver driverToUpdate = getDriverEntityById(id);

        driverToUpdate.setRating(rating);

        return driverMapper.entityToDto(
                driverRepository.save(driverToUpdate));
    }

    @Override
    public void deleteDriverById(String id) {
        driverValidator.validateExistenceOfDriverWithId(id);

        driverRepository.deleteById(id);
    }

    private Driver getDriverEntityById(String id) {
        return driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.DRIVER_WITH_ID_NOT_FOUND,
                        id));
    }
}
