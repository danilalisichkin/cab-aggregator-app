package com.cabaggregator.ratingservice.service.impl;

import com.cabaggregator.ratingservice.core.constant.ApplicationMessages;
import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.core.enums.sort.PassengerRateSortField;
import com.cabaggregator.ratingservice.core.mapper.PageMapper;
import com.cabaggregator.ratingservice.core.mapper.PassengerRateMapper;
import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.exception.ForbiddenException;
import com.cabaggregator.ratingservice.exception.ResourceNotFoundException;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import com.cabaggregator.ratingservice.security.enums.UserRole;
import com.cabaggregator.ratingservice.security.util.SecurityUtil;
import com.cabaggregator.ratingservice.service.PassengerRateService;
import com.cabaggregator.ratingservice.util.PageRequestBuilder;
import com.cabaggregator.ratingservice.util.UserRoleExtractor;
import com.cabaggregator.ratingservice.validator.PassengerRateValidator;
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
public class PassengerRateServiceImpl implements PassengerRateService {

    private final SecurityUtil securityUtil;

    private final UserRoleExtractor userRoleExtractor;

    private final PassengerRateRepository passengerRateRepository;

    private final PassengerRateMapper passengerRateMapper;

    private final PageMapper pageMapper;

    private final PassengerRateValidator passengerRateValidator;

    /**
     * Returns computed rating of Passenger.
     * Rating is aggregated average rate value.
     **/
    @Override
    public Double getPassengerRating(UUID passengerId) {
        return passengerRateRepository
                .findAverageRateByPassengerId(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PASSENGER_RATING_NOT_FOUND));
    }

    /**
     * Returns page of Passenger rates.
     * Used by Passenger or Admin.
     **/
    @Override
    public PageDto<PassengerRateDto> getPageOfPassengerRates(
            UUID passengerId, Integer offset, Integer limit, PassengerRateSortField sortBy, Sort.Direction sortOrder) {

        validateUserIsRequestedPassengerOrAdmin(passengerId);

        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<PassengerRate> passengerRates = passengerRateRepository.findAllByPassengerId(passengerId, pageRequest);

        return pageMapper.pageToPageDto(
                passengerRateMapper.entityPageToDtoPage(passengerRates));
    }

    /**
     * Returns specified Passenger rate.
     * Used by Passenger or Admin.
     **/
    @Override
    public PassengerRateDto getPassengerRate(UUID passengerId, ObjectId rideId) {
        validateUserIsRequestedPassengerOrAdmin(passengerId);

        return passengerRateMapper.entityToDto(
                getPassengerRateEntity(passengerId, rideId));
    }

    /**
     * Creates new Passenger rate with blank rating.
     **/
    @Override
    @Transactional
    public PassengerRateDto savePassengerRate(PassengerRateAddingDto addingDto) {
        passengerRateValidator.validatePassengerRateUniqueness(addingDto.passengerId(), addingDto.rideId());

        PassengerRate newPassengerRate = passengerRateMapper.dtoToEntity(addingDto);

        return passengerRateMapper.entityToDto(
                passengerRateRepository.save(newPassengerRate));
    }

    /**
     * Sets Passenger rating for specified ride.
     * Used by Driver.
     **/
    @Override
    @Transactional
    public PassengerRateDto setPassengerRate(UUID passengerId, ObjectId rideId, PassengerRateSettingDto settingDto) {
        PassengerRate passengerRateToUpdate = getPassengerRateEntity(passengerId, rideId);

        UUID userId = securityUtil.getUserIdFromSecurityContext();
        passengerRateValidator.validateDriverParticipation(passengerRateToUpdate, userId);
        passengerRateValidator.validatePassengerRateSetting(passengerRateToUpdate);

        passengerRateMapper.updateEntityFromDto(settingDto, passengerRateToUpdate);

        return passengerRateMapper.entityToDto(
                passengerRateRepository.save(passengerRateToUpdate));
    }

    /**
     * Validates that current user is requested Passenger (resource owner) or Admin.
     **/
    private void validateUserIsRequestedPassengerOrAdmin(UUID passengerId) {
        UUID userId = securityUtil.getUserIdFromSecurityContext();
        UserRole userRole = userRoleExtractor.extractCurrentUserRole();

        if (!userId.equals(passengerId) && userRole.equals(UserRole.PASSENGER)) {
            throw new ForbiddenException(ApplicationMessages.CANT_GET_RATES_OF_OTHER_USER);
        }
    }

    /**
     * Returns existing Passenger rate or throws exception if it doesn't exist.
     **/
    private PassengerRate getPassengerRateEntity(UUID passengerId, ObjectId rideId) {
        return passengerRateRepository
                .findByPassengerIdAndRideId(passengerId, rideId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_RATE_WITH_RIDE_ID_NOT_FOUND,
                        rideId));
    }
}
