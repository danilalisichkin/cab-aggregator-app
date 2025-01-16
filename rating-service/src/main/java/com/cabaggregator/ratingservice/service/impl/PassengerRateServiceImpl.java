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
import com.cabaggregator.ratingservice.exception.ResourceNotFoundException;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import com.cabaggregator.ratingservice.service.PassengerRateService;
import com.cabaggregator.ratingservice.util.PageRequestBuilder;
import com.cabaggregator.ratingservice.validator.PassengerRateValidator;
import com.cabaggregator.ratingservice.validator.UserRoleValidator;
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

    private final PassengerRateRepository passengerRateRepository;

    private final PassengerRateMapper passengerRateMapper;

    private final PageMapper pageMapper;

    private final PassengerRateValidator passengerRateValidator;

    private final UserRoleValidator userRoleValidator;

    @Override
    public Double getPassengerRating(UUID passengerId) {
        return passengerRateRepository
                .findAverageRateByPassengerId(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PASSENGER_RATING_NOT_FOUND));
    }

    @Override
    public PageDto<PassengerRateDto> getPageOfPassengerRates(
            UUID passengerId, Integer offset, Integer limit, PassengerRateSortField sortBy, Sort.Direction sortOrder) {

        userRoleValidator.validateUserIsPassengerOrAdmin(passengerId);

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        Page<PassengerRate> passengerRates = passengerRateRepository.findAllByPassengerId(passengerId, pageRequest);

        return pageMapper.pageToPageDto(
                passengerRateMapper.entityPageToDtoPage(passengerRates));
    }

    @Override
    public PassengerRateDto getPassengerRate(UUID passengerId, ObjectId rideId) {
        userRoleValidator.validateUserIsPassengerOrAdmin(passengerId);

        return passengerRateMapper.entityToDto(
                getPassengerRateEntity(passengerId, rideId));
    }

    @Override
    @Transactional
    public PassengerRateDto savePassengerRate(PassengerRateAddingDto addingDto) {
        passengerRateValidator.validatePassengerRateUniqueness(addingDto.passengerId(), addingDto.rideId());

        PassengerRate newPassengerRate = passengerRateMapper.dtoToEntity(addingDto);

        return passengerRateMapper.entityToDto(
                passengerRateRepository.save(newPassengerRate));
    }

    @Override
    @Transactional
    public PassengerRateDto setPassengerRate(UUID passengerId, ObjectId rideId, PassengerRateSettingDto settingDto) {
        PassengerRate passengerRateToUpdate = getPassengerRateEntity(passengerId, rideId);
        passengerRateValidator.validateDriverParticipation(passengerRateToUpdate);
        passengerRateValidator.validatePassengerRateSetting(passengerRateToUpdate);

        passengerRateMapper.updateEntityFromDto(settingDto, passengerRateToUpdate);

        return passengerRateMapper.entityToDto(
                passengerRateRepository.save(passengerRateToUpdate));
    }

    private PassengerRate getPassengerRateEntity(UUID passengerId, ObjectId rideId) {
        return passengerRateRepository
                .findByPassengerIdAndRideId(passengerId, rideId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_RATE_WITH_RIDE_ID_NOT_FOUND,
                        rideId));
    }
}
