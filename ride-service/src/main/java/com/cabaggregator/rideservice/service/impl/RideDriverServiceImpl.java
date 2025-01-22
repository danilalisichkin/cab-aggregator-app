package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.core.mapper.PageMapper;
import com.cabaggregator.rideservice.core.mapper.RideMapper;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.security.enums.UserRole;
import com.cabaggregator.rideservice.service.RideDriverService;
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

@Service
@RequiredArgsConstructor
public class RideDriverServiceImpl implements RideDriverService {

    private final RideStatusChangingManager rideStatusChangingManager;

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    private final PageMapper pageMapper;

    /**
     * Returns page of driver rides.
     * Has optional RideStatus parameter.
     **/
    @Override
    @PreAuthorize("hasRole('ADMIN') or #driverId == authentication.principal")
    public PageDto<RideDto> getPageOfDriverRides(
            UUID driverId, Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder,
            RideStatus status) {

        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> driverRides = status == null
                ? rideRepository.findAllByDriverId(driverId, pageRequest)
                : rideRepository.findAllByDriverIdAndStatus(driverId, status, pageRequest);

        return pageMapper.pageToPageDto(
                rideMapper.entityPageToDtoPage(driverRides));
    }

    /**
     * Returns specified driver ride.
     * Used by Driver or Admin.
     **/
    @Override
    @PreAuthorize("hasRole('ADMIN') or #driverId == authentication.principal")
    public RideDto getRide(UUID driverId, ObjectId id) {
        Ride ride = getRideEntity(id, driverId);

        return rideMapper.entityToDto(ride);
    }

    /**
     * Updates the ride status.
     * Allows to manage ride lifecycle.
     **/
    @Override
    @Transactional
    @PreAuthorize("#driverId == authentication.principal")
    public RideDto changeRideStatus(UUID driverId, ObjectId id, RideStatus status) {
        Ride rideToUpdate = getRideEntity(id, driverId);

        rideStatusChangingManager.processRideStatusChanging(rideToUpdate, UserRole.DRIVER, status);

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    /**
     * Return existing ride or throws exception if it doesn't exist.
     **/
    private Ride getRideEntity(ObjectId id, UUID driverId) {
        return rideRepository
                .findByIdAndDriverId(id, driverId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.DRIVER_RIDE_WITH_ID_NOT_FOUND,
                        id.toString(), driverId.toString()));
    }
}
