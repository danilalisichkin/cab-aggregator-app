package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.core.mapper.PageMapper;
import com.cabaggregator.rideservice.core.mapper.RideMapper;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.security.enums.UserRole;
import com.cabaggregator.rideservice.security.util.SecurityUtil;
import com.cabaggregator.rideservice.service.RideDriverService;
import com.cabaggregator.rideservice.strategy.manager.RideStatusChangingManager;
import com.cabaggregator.rideservice.util.PageRequestBuilder;
import com.cabaggregator.rideservice.util.UserRoleExtractor;
import com.cabaggregator.rideservice.validator.RideValidator;
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
public class RideDriverServiceImpl implements RideDriverService {

    private final SecurityUtil securityUtil;

    private final UserRoleExtractor userRoleExtractor;

    private final RideStatusChangingManager rideStatusChangingManager;

    private final RideValidator rideValidator;

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    private final PageMapper pageMapper;

    /**
     * Returns page of driver rides.
     * Has optional RideStatus parameter.
     **/
    @Override
    public PageDto<RideDto> getPageOfDriverRides(
            UUID driverId, Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder,
            RideStatus status) {

        UUID userId = securityUtil.getUserIdFromSecurityContext();
        if (!userId.equals(driverId)) {
            throw new ForbiddenException(ApplicationMessages.CANT_GET_RIDES_OF_OTHER_USER);
        }

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
    public RideDto getRide(UUID driverId, ObjectId id) {
        Ride ride = getRideEntity(id);

        UUID userId = securityUtil.getUserIdFromSecurityContext();
        UserRole userRole = userRoleExtractor.extractCurrentUserRole();

        if (userRole.equals(UserRole.DRIVER)) {
            if (!userId.equals(driverId)) {
                throw new ForbiddenException(ApplicationMessages.CANT_GET_RIDES_OF_OTHER_USER);
            }
            rideValidator.validateDriverParticipation(ride, userId);
        }

        return rideMapper.entityToDto(ride);
    }

    /**
     * Updates the ride status.
     * Allows to manage ride lifecycle.
     **/
    @Override
    @Transactional
    public RideDto changeRideStatus(UUID driverId, ObjectId id, RideStatus status) {
        Ride rideToUpdate = getRideEntity(id);

        rideStatusChangingManager.processRideStatusChanging(rideToUpdate, UserRole.DRIVER, status);

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    /**
     * Return existing ride or throws exception if it doesn't exist.
     **/
    private Ride getRideEntity(ObjectId id) {
        return rideRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_WITH_ID_NOT_FOUND,
                        id.toString()));
    }
}
