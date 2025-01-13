package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.price.PriceRecalculationDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.route.RouteSummary;
import com.cabaggregator.rideservice.core.enums.PaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.entity.Ride;
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

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    private final RideValidator rideValidator;

    private final RideStatusChangingManager rideStatusChangingManager;

    private final PriceService priceService;

    private final RouteService routeService;

    private final RideMapper rideMapper;

    private final PageMapper pageMapper;

    private final SecurityUtil securityUtil;

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
    public PageDto<RideDto> getPageOfRequestedRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> requestedRides = rideRepository.findAllByStatus(RideStatus.REQUESTED, pageRequest);

        return pageMapper.pageToPageDto(
                rideMapper.entityPageToDtoPage(requestedRides));
    }

    /**
     * Returns specified ride.
     * Used by Passenger or Driver.
     **/
    @Override
    public RideDto getRide(ObjectId id) {
        Ride ride = getRideEntity(id);

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
     * Accepts the ride by driver.
     **/
    @Override
    @Transactional
    public RideDto acceptRide(ObjectId id) {
        Ride rideToAccept = getRideEntity(id);

        rideStatusChangingManager.processRideStatusChanging(rideToAccept, UserRole.DRIVER, RideStatus.ARRIVING);

        return rideMapper.entityToDto(
                rideRepository.save(rideToAccept));
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
     * Returns existing ride or throws exception if it doesn't exist.
     **/
    private Ride getRideEntity(ObjectId id) {
        return rideRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_WITH_ID_NOT_FOUND,
                        id.toString()));
    }
}
