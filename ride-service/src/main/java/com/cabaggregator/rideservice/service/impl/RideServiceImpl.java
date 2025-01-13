package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.price.PriceRecalculationDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.dto.route.RouteSummary;
import com.cabaggregator.rideservice.core.enums.PaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.mapper.PageMapper;
import com.cabaggregator.rideservice.mapper.RideMapper;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.security.enums.UserRole;
import com.cabaggregator.rideservice.security.util.SecurityUtil;
import com.cabaggregator.rideservice.service.PriceService;
import com.cabaggregator.rideservice.service.RideService;
import com.cabaggregator.rideservice.service.RouteService;
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

import java.util.List;
import java.util.UUID;

import static com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker.isPrepared;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    private final RideValidator rideValidator;

    private final PriceService priceService;

    private final RouteService routeService;

    private final RideStatusChangingManager rideStatusChangingManager;

    private final RideMapper rideMapper;

    private final PageMapper pageMapper;

    private final SecurityUtil securityUtil;

    private final UserRoleExtractor userRoleExtractor;

    /**
     * Returns page of existing rides.
     * Used by Admin.
     * Has optional RideStatus parameter.
     **/
    @Override
    public PageDto<RideDto> getPageOfRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder, RideStatus status) {

        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> rides = status == null
                ? rideRepository.findAll(pageRequest)
                : rideRepository.findAllByStatus(status, pageRequest);

        return pageMapper.pageToPageDto(
                rideMapper.entityPageToDtoPage(rides));
    }

    /**
     * Returns page of available (requested) rides.
     * Used by Driver.
     **/
    @Override
    public PageDto<RideDto> getPageOfAvailableRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> availableRides = rideRepository.findAllByStatus(RideStatus.REQUESTED, pageRequest);

        return pageMapper.pageToPageDto(
                rideMapper.entityPageToDtoPage(availableRides));
    }

    /**
     * Returns page of driver rides.
     * Used by Driver or Admin.
     * Has optional RideStatus parameter.
     **/
    @Override
    public PageDto<RideDto> getPageOfDriverRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder, RideStatus status,
            UUID driverId) {

        UserRole userRole = userRoleExtractor.extractCurrentUserRole();
        UUID userId = securityUtil.getUserIdFromSecurityContext();
        if (userRole.equals(UserRole.DRIVER) && !userId.equals(driverId)) {
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
     * Returns page of passenger rides.
     * Used by Passenger or Admin.
     * Has optional RideStatus parameter.
     **/
    @Override
    public PageDto<RideDto> getPageOfPassengerRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder, RideStatus status,
            UUID passengerId) {

        UserRole userRole = userRoleExtractor.extractCurrentUserRole();
        UUID userId = securityUtil.getUserIdFromSecurityContext();
        if (userRole.equals(UserRole.PASSENGER) && !userId.equals(passengerId)) {
            throw new ForbiddenException(ApplicationMessages.CANT_GET_RIDES_OF_OTHER_USER);
        }

        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> passengerRides = status == null
                ? rideRepository.findAllByPassengerId(passengerId, pageRequest)
                : rideRepository.findAllByPassengerIdAndStatus(passengerId, status, pageRequest);

        return pageMapper.pageToPageDto(
                rideMapper.entityPageToDtoPage(passengerRides));
    }

    /**
     * Returns specified ride.
     * Used by Passenger or Driver.
     **/
    @Override
    public RideDto getRide(ObjectId id) {
        Ride ride = getRideEntity(id);

        UserRole userRole = userRoleExtractor.extractCurrentUserRole();
        UUID userId = securityUtil.getUserIdFromSecurityContext();
        if (userRole.equals(UserRole.PASSENGER)) {
            rideValidator.validatePassengerParticipation(ride, userId);
        } else if (userRole.equals(UserRole.DRIVER)) {
            rideValidator.validateDriverParticipation(ride, userId);
        }

        return rideMapper.entityToDto(ride);
    }

    /**
     * Creates a new ride.
     * Used by Passenger to order new ride.
     **/
    @Override
    @Transactional
    public RideDto createRide(RideAddingDto addingDto) {
        rideValidator.validateAddresses(addingDto.pickUpAddress(), addingDto.dropOffAddress());

        UUID userId = securityUtil.getUserIdFromSecurityContext();
        rideValidator.validatePassengerFreedom(userId);

        Ride rideToCreate = initDefaultRide(addingDto, userId);

        setRouteSummary(rideToCreate);
        rideToCreate = rideRepository.save(rideToCreate);

        calculateBasePrice(rideToCreate);

        if (rideToCreate.getPromoCode() != null) {
            recalculatePriceWithDiscount(rideToCreate);
        }

        return rideMapper.entityToDto(
                rideRepository.save(rideToCreate));
    }

    /**
     * Updates the existing ride.
     * Used by Passenger to change some ride data before ride is started.
     **/
    @Override
    @Transactional
    public RideDto updateRide(ObjectId id, RideUpdatingDto updatingDto) {
        Ride rideToUpdate = getRideEntity(id);

        if (!isPrepared(rideToUpdate.getStatus())) {
            throw new BadRequestException(ApplicationMessages.CANT_CHANGE_RIDE_WHEN_IT_REQUESTED);
        }

        rideMapper.updateEntityFromOrderDto(updatingDto, rideToUpdate);

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    /**
     * Updates the ride status.
     * Allows to manage ride lifecycle.
     * Used by Passenger or Driver.
     **/
    @Override
    @Transactional
    public RideDto changeRideStatus(ObjectId id, RideStatus status) {
        Ride rideToUpdate = getRideEntity(id);

        UserRole userRole = userRoleExtractor.extractCurrentUserRole();
        rideStatusChangingManager.processRideStatusChanging(rideToUpdate, userRole, status);

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    /**
     * Initializes ride with default parameters.
     * Used before performing any operations on new ordered ride.
     **/
    private Ride initDefaultRide(RideAddingDto addingDto, UUID userId) {
        Ride ride = rideMapper.dtoToEntity(addingDto);
        ride.setPassengerId(userId);
        ride.setPaymentStatus(PaymentStatus.PENDING);
        ride.setStatus(RideStatus.PREPARED);

        return ride;
    }

    /**
     * Sets route summary: distance and estimated duration.
     **/
    private void setRouteSummary(Ride ride) {
        List<List<Double>> routeCoordinates = List.of(
                ride.getPickUpAddress().coordinates(),
                ride.getDropOffAddress().coordinates());

        RouteSummary routeSummary = routeService.getRouteSummary(routeCoordinates);

        ride.setDistance(routeSummary.distance());
        ride.setEstimatedDuration(routeSummary.duration());
    }

    /**
     * Calculates ride base price.
     **/
    private void calculateBasePrice(Ride ride) {
        PriceCalculationRequest request = rideMapper.entityToPriceCalculationRequest(ride);

        Long basePrice = priceService.calculateBasePrice(request);

        ride.setPrice(basePrice);
    }

    /**
     * Recalculates ride price by applying discount.
     **/
    private void recalculatePriceWithDiscount(Ride ride) {
        PriceRecalculationDto recalculationDto = rideMapper.entityToPriceRecalculationDto(ride);

        Long priceWithDiscount = priceService.recalculatePriceWithDiscount(recalculationDto);

        ride.setPrice(priceWithDiscount);
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
