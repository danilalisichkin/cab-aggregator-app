package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.enums.UserRole;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.core.mapper.PageMapper;
import com.cabaggregator.rideservice.core.mapper.RideMapper;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.service.DurationCalculationService;
import com.cabaggregator.rideservice.service.PriceCalculationService;
import com.cabaggregator.rideservice.service.PromoCodeService;
import com.cabaggregator.rideservice.service.RideRateService;
import com.cabaggregator.rideservice.service.RideService;
import com.cabaggregator.rideservice.service.UserCredentialsService;
import com.cabaggregator.rideservice.util.PageRequestBuilder;
import com.cabaggregator.rideservice.validator.PromoCodeValidator;
import com.cabaggregator.rideservice.validator.RideValidator;
import com.cabaggregator.rideservice.validator.UserRoleValidator;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final UserCredentialsService userCredentialsService;
    private final PriceCalculationService priceCalculationService;
    private final DurationCalculationService durationCalculationService;
    private final RideRateService rideRateService;
    private final PromoCodeService promoCodeService;

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;
    private final PageMapper pageMapper;

    private final RideValidator rideValidator;
    private final PromoCodeValidator promoCodeValidator;
    private final UserRoleValidator userRoleValidator;

    @Override
    public RideDto getRideById(String accessToken, ObjectId id) {
        return rideMapper.entityToDto(
                getRideEntityById(id));
    }

    @Override
    public RideRateDto getRideRate(String accessToken, ObjectId id) {
        validateReviewerParticipation(accessToken, id);

        return rideRateService.getRideRateByRideId(id);
    }

    @Override
    @Transactional
    public RideRateDto setRideRate(String accessToken, ObjectId id, Integer rate) {
        validateReviewerParticipation(accessToken, id);
        //TODO: add integration with passenger & driver services: calculate and set rating

        return rideRateService.saveRideRate(id, getUserRole(accessToken), rate);
    }

    @Override
    public PageDto<RideDto> getPageOfRides(
            String accessToken, Integer offset, Integer limit, RideSortField sortField, Sort.Direction sortOrder,
            RideStatus status) {

        PageRequest request = PageRequestBuilder.buildPageRequest(offset, limit, sortField.getValue(), sortOrder);

        UserRole userRole = getUserRole(accessToken);
        String userId = userCredentialsService.getUserId(accessToken);

        if (userRole.equals(UserRole.ADMIN)) {
            return getRidesForAdmin(status, request);
        } else if (userRole.equals(UserRole.PASSENGER)) {
            return getRidesForPassenger(status, userId, request);
        } else if (userRole.equals(UserRole.DRIVER)) {
            return getRidesForDriver(status, userId, request);
        } else {
            throw new ForbiddenException(
                    ApplicationMessages.USER_MUST_HAVE_ROLE,
                    UserRole.PASSENGER.getValue() + " / " +
                            UserRole.DRIVER.getValue() + " / " +
                            UserRole.ADMIN.getValue());
        }
    }

    @Override
    @Transactional
    public RideDto orderRide(String accessToken, RideOrderAddingDto addingDto) {
        userRoleValidator.validateUserIsPassenger(getUserRole(accessToken));

        rideValidator.validateAddresses(addingDto.pickupAddress(), addingDto.destinationAddress());

        Ride ride = rideMapper.dtoToEntity(addingDto);
        ride.setStatus(RideStatus.PREPARED);
        ride.setOrderTime(LocalDateTime.now());
        ride.setPrice(
                priceCalculationService.calculatePrice(
                        addingDto.pickupAddress(),
                        addingDto.destinationAddress(),
                        addingDto.serviceCategory()));
        ride.setEstimatedDuration(
                durationCalculationService.calculateDuration(
                        addingDto.pickupAddress(),
                        addingDto.destinationAddress()));

        return rideMapper.entityToDto(
                rideRepository.save(ride));
    }

    @Override
    @Transactional
    public RideDto updateRideOrder(String accessToken, ObjectId id, RideOrderUpdatingDto updatingDto) {
        userRoleValidator.validateUserIsPassenger(getUserRole(accessToken));

        rideValidator.validateAddresses(updatingDto.pickupAddress(), updatingDto.destinationAddress());

        Ride rideToUpdate = getRideEntityById(id);
        rideValidator.validatePassengerParticipation(rideToUpdate, getUserId(accessToken));

        rideToUpdate.setPrice(
                priceCalculationService.calculatePrice(
                        updatingDto.pickupAddress(),
                        updatingDto.destinationAddress(),
                        rideToUpdate.getServiceCategory()));
        rideToUpdate.setEstimatedDuration(
                durationCalculationService.calculateDuration(
                        updatingDto.pickupAddress(),
                        updatingDto.destinationAddress()));
        rideToUpdate.setPaymentMethod(updatingDto.paymentMethod());

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    @Override
    @Transactional
    public RideDto changeRideStatus(String accessToken, ObjectId id) {
        UserRole userRole = getUserRole(accessToken);
        userRoleValidator.validateUserIsPassengerOrDriver(getUserRole(accessToken));

        Ride rideToUpdate = getRideEntityById(id);
        if (userRole.equals(UserRole.PASSENGER)) {
            rideValidator.validatePassengerParticipation(rideToUpdate, getUserId(accessToken));
        } else {
            rideValidator.validateDriverParticipation(rideToUpdate, getUserId(accessToken));
        }

        changeRideStatusAccordingToRole(rideToUpdate, userRole);

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    @Override
    @Transactional
    public RideDto applyPromoCode(String accessToken, ObjectId id, String codeValue) {
        userRoleValidator.validateUserIsPassenger(getUserRole(accessToken));

        Ride rideToUpdate = getRideEntityById(id);
        rideValidator.validatePromoCodeApplication(rideToUpdate);

        rideValidator.validatePassengerParticipation(rideToUpdate, getUserId(accessToken));

        PromoCodeDto promoCodeDto = promoCodeService.getPromoCodeByValue(codeValue);
        promoCodeValidator.validatePromoCodeExpiration(promoCodeDto.endDate());

        BigDecimal newPrice = priceCalculationService.applyDiscount(rideToUpdate.getPrice(), promoCodeDto.discount());

        rideToUpdate.setPromoCode(codeValue);
        rideToUpdate.setPrice(newPrice);

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    private Ride getRideEntityById(ObjectId id) {
        return rideRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_WITH_ID_NOT_FOUND,
                        id));
    }

    private PageDto<RideDto> getRidesForAdmin(RideStatus status, PageRequest request) {
        return pageMapper.pageToPageDto(
                rideMapper.entityPageToDtoPage(
                        rideRepository.findByStatus(status, request)));
    }

    private PageDto<RideDto> getRidesForPassenger(RideStatus status, String userId, PageRequest request) {
        if (status.equals(RideStatus.COMPLETED)) {
            return pageMapper.pageToPageDto(
                    rideMapper.entityPageToDtoPage(
                            rideRepository.findByStatusAndPassengerId(status, userId, request)));
        } else {
            throw new ForbiddenException(
                    ApplicationMessages.CANT_GET_RIDES_WITH_STATUS,
                    status.getValue());
        }
    }

    private PageDto<RideDto> getRidesForDriver(RideStatus status, String userId, PageRequest request) {
        if (status.equals(RideStatus.COMPLETED)) {
            return pageMapper.pageToPageDto(
                    rideMapper.entityPageToDtoPage(
                            rideRepository.findByStatusAndDriverId(status, userId, request)));
        } else if (status.equals(RideStatus.REQUESTED)) {
            return pageMapper.pageToPageDto(
                    rideMapper.entityPageToDtoPage(
                            rideRepository.findByStatus(status, request)));
        } else {
            throw new ForbiddenException(
                    ApplicationMessages.CANT_GET_RIDES_WITH_STATUS,
                    status.getValue());
        }
    }

    private void changeRideStatusAccordingToRole(Ride ride, UserRole role) {
        int statusIndex = ride.getStatus().getId();

        if (role.equals(UserRole.DRIVER)) {
            if (statusIndex > RideStatus.IN_PROGRESS.getId()) {
                throw new ForbiddenException(
                        ApplicationMessages.CANT_CHANGE_RIDE_STATUS);
            }
            ride.setStatus(RideStatus.getById(statusIndex + 1));
        } else if (role.equals(UserRole.PASSENGER)) {
            if (statusIndex == RideStatus.ARRIVING.getId()) {
                ride.setStatus(RideStatus.CANCELED);
            } else if (statusIndex == RideStatus.PREPARED.getId()) {
                ride.setStatus(RideStatus.REQUESTED);
            } else {
                throw new ForbiddenException(
                        ApplicationMessages.CANT_CANCEL_RIDE);
            }
        }
    }

    private UserRole getUserRole(String accessToken) {
        return userCredentialsService.getUserRole(accessToken)
                .orElseThrow(() -> new BadRequestException(
                        ApplicationMessages.NO_USER_ROLE_PROVIDED));
    }

    private String getUserId(String accessToken) {
        return userCredentialsService.getUserId(accessToken);
    }

    private void validateReviewerParticipation(String accessToken, ObjectId rideId) {
        UserRole userRole = getUserRole(accessToken);
        userRoleValidator.validateUserIsPassengerOrDriver(userRole);

        Ride ride = getRideEntityById(rideId);
        String userId = getUserId(accessToken);

        if (userRole.equals(UserRole.PASSENGER)) {
            rideValidator.validatePassengerParticipation(ride, userId);
        } else {
            rideValidator.validateDriverParticipation(ride, userId);
        }
    }
}
