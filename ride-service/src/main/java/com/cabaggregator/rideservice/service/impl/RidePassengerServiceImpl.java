package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.core.mapper.PageMapper;
import com.cabaggregator.rideservice.core.mapper.RideMapper;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.security.enums.UserRole;
import com.cabaggregator.rideservice.service.RidePassengerService;
import com.cabaggregator.rideservice.strategy.manager.RideStatusChangingManager;
import com.cabaggregator.rideservice.util.PageRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker.isPrepared;

@Service
@RequiredArgsConstructor
public class RidePassengerServiceImpl implements RidePassengerService {

    private final RideStatusChangingManager rideStatusChangingManager;

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    private final PageMapper pageMapper;

    /**
     * Returns page of passenger rides.
     * Has optional RideStatus parameter.
     **/
    @Override
    @PreAuthorize("hasRole('ADMIN') or #passengerId == authentication.principal")
    public PageDto<RideDto> getPageOfPassengerRides(
            UUID passengerId, Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder,
            RideStatus status) {

        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> passengerRides = status == null
                ? rideRepository.findAllByPassengerId(passengerId, pageRequest)
                : rideRepository.findAllByPassengerIdAndStatus(passengerId, status, pageRequest);

        return pageMapper.pageToPageDto(
                rideMapper.entityPageToDtoPage(passengerRides));
    }

    /**
     * Returns specified passenger ride.
     **/
    @Override
    @PreAuthorize("hasRole('ADMIN') or #passengerId == authentication.principal")
    public RideDto getRide(UUID passengerId, ObjectId id) {
        Ride ride = getRideEntity(id, passengerId);

        return rideMapper.entityToDto(ride);
    }

    /**
     * Updates the existing ride.
     * Used to change some ride data before ride is started.
     **/
    @Override
    @Transactional
    @PreAuthorize("#passengerId == authentication.principal")
    public RideDto updateRide(UUID passengerId, ObjectId id, RideUpdatingDto updatingDto) {
        Ride rideToUpdate = getRideEntity(id, passengerId);

        if (!isPrepared(rideToUpdate.getStatus())) {
            throw new BadRequestException(ApplicationMessages.CANT_CHANGE_RIDE_WHEN_IT_REQUESTED);
        }

        rideMapper.updateEntityFromDto(updatingDto, rideToUpdate);

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    /**
     * Updates the ride status.
     * Allows to manage ride lifecycle.
     **/
    @Override
    @Transactional
    @PreAuthorize("#passengerId == authentication.principal")
    public RideDto changeRideStatus(UUID passengerId, ObjectId id, RideStatus status) {
        Ride rideToUpdate = getRideEntity(id, passengerId);

        rideStatusChangingManager.processRideStatusChanging(rideToUpdate, UserRole.PASSENGER, status);

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    /**
     * Returns existing ride or throws exception if it doesn't exist.
     **/
    private Ride getRideEntity(ObjectId id, UUID passengerId) {
        return rideRepository
                .findByIdAndPassengerId(id, passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PASSENGER_RIDE_WITH_ID_NOT_FOUND,
                        id.toString(), passengerId.toString()));
    }
}
