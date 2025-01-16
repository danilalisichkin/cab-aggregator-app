package com.cabaggregator.ratingservice.service.impl;

import com.cabaggregator.ratingservice.core.constant.ApplicationMessages;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateSettingDto;
import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.enums.sort.DriverRateSortField;
import com.cabaggregator.ratingservice.core.mapper.DriverRateMapper;
import com.cabaggregator.ratingservice.core.mapper.PageMapper;
import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.exception.ForbiddenException;
import com.cabaggregator.ratingservice.exception.ResourceNotFoundException;
import com.cabaggregator.ratingservice.repository.DriverRateRepository;
import com.cabaggregator.ratingservice.security.enums.UserRole;
import com.cabaggregator.ratingservice.security.util.SecurityUtil;
import com.cabaggregator.ratingservice.service.DriverRateService;
import com.cabaggregator.ratingservice.util.PageRequestBuilder;
import com.cabaggregator.ratingservice.util.UserRoleExtractor;
import com.cabaggregator.ratingservice.validator.DriverRateValidator;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverRateServiceImpl implements DriverRateService {

    private final SecurityUtil securityUtil;

    private final UserRoleExtractor userRoleExtractor;

    private final DriverRateRepository driverRateRepository;

    private final DriverRateMapper driverRateMapper;

    private final PageMapper pageMapper;

    private final DriverRateValidator driverRateValidator;

    /**
     * Returns computed rating of Driver.
     * Rating is aggregated average rate value.
     **/
    @Override
    public Double getDriverRating(UUID driverId) {
        return driverRateRepository
                .findAverageRateByDriverId(driverId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.DRIVER_RATING_NOT_FOUND));
    }

    /**
     * Returns page of Driver rates.
     * Used by Driver or Admin.
     **/
    @Override
    public PageDto<DriverRateDto> getPageOfDriverRates(
            UUID driverId, Integer offset, Integer limit, DriverRateSortField sortBy, Sort.Direction sortOrder) {

        validateUserIsRequestedDriverOrAdmin(driverId);

        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<DriverRate> driverRates = driverRateRepository.findAllByDriverId(driverId, pageRequest);

        return pageMapper.pageToPageDto(
                driverRateMapper.entityPageToDtoPage(driverRates));
    }

    /**
     * Returns specified Driver rate.
     * Used by Driver or Admin.
     **/
    @Override
    public DriverRateDto getDriverRate(UUID driverId, ObjectId rideId) {
        validateUserIsRequestedDriverOrAdmin(driverId);

        return driverRateMapper.entityToDto(
                getDriverRateEntity(driverId, rideId));
    }

    /**
     * Creates new Driver rate with blank rating and feedback options.
     **/
    @Override
    @Transactional
    public DriverRateDto saveDriverRate(DriverRateAddingDto addingDto) {
        driverRateValidator.validateDriverRateUniqueness(addingDto.driverId(), addingDto.rideId());

        DriverRate newDriverRate = driverRateMapper.dtoToEntity(addingDto);

        return driverRateMapper.entityToDto(
                driverRateRepository.save(newDriverRate));
    }

    /**
     * Sets Driver rating and feedback options for specified ride.
     * Used by Passenger.
     **/
    @Override
    @Transactional
    public DriverRateDto setDriverRate(UUID driverId, ObjectId rideId, DriverRateSettingDto settingDto) {
        DriverRate driverRateToUpdate = getDriverRateEntity(driverId, rideId);

        UUID userId = securityUtil.getUserIdFromSecurityContext();
        driverRateValidator.validatePassengerParticipation(driverRateToUpdate, userId);
        driverRateValidator.validateDriverRateSetting(driverRateToUpdate);

        driverRateMapper.updateEntityFromDto(settingDto, driverRateToUpdate);

        return driverRateMapper.entityToDto(
                driverRateRepository.save(driverRateToUpdate));
    }

    /**
     * Validates that current user is requested Driver (resource owner) or Admin.
     **/
    private void validateUserIsRequestedDriverOrAdmin(UUID driverId) {
        UUID userId = securityUtil.getUserIdFromSecurityContext();
        UserRole userRole = userRoleExtractor.extractCurrentUserRole();

        if (!userId.equals(driverId) && userRole.equals(UserRole.DRIVER)) {
            throw new ForbiddenException(ApplicationMessages.CANT_GET_RATES_OF_OTHER_USER);
        }
    }

    /**
     * Returns existing Driver rate or throws exception if it doesn't exist.
     **/
    private DriverRate getDriverRateEntity(UUID driverId, ObjectId rideId) {
        return driverRateRepository
                .findByDriverIdAndRideId(driverId, rideId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_RATE_WITH_RIDE_ID_NOT_FOUND,
                        rideId));
    }
}
