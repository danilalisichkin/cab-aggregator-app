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
     * Returns the computed rating of a Passenger.
     * The rating is the aggregated average rate value based on Passenger ratings.
     *
     * @param passengerId The UUID of the Passenger whose rating is being retrieved.
     * @return The computed average rating of the Passenger.
     * @throws ResourceNotFoundException if no rating is found for the specified Passenger.
     */
    @Override
    public Double getPassengerRating(UUID passengerId) {
        return passengerRateRepository
                .findAverageRateByPassengerId(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PASSENGER_RATING_NOT_FOUND));
    }

    /**
     * Returns a paginated list of Passenger ratings.
     * This method is accessible by the Passenger or an Admin.
     *
     * @param passengerId The UUID of the passenger whose ratings are being retrieved.
     * @param offset The page number for pagination (0-based).
     * @param limit The maximum number of ratings per page.
     * @param sortBy The field by which to sort the ratings.
     * @param sortOrder The direction of sorting (ascending or descending).
     * @return A PageDto containing a list of PassengerRateDto objects representing the ratings.
     * @throws ForbiddenException if Passenger tries to retrieve other Passenger's rates.
     */
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
     * Returns a specific Passenger's rate for a given ride.
     * This method is accessible by the Passenger or an Admin.
     *
     * @param passengerId The UUID of the Passenger whose rating is being retrieved.
     * @param rideId The ObjectId of the ride for which the rating is being fetched.
     * @return A PassengerRateDto representing the rating of the passenger for the specific ride.
     * @throws ResourceNotFoundException if no rating is found for the specified Passenger and ride combination.
     * @throws ForbiddenException if Passenger tries to retrieve other Passenger's rates.
     */
    @Override
    public PassengerRateDto getPassengerRate(UUID passengerId, ObjectId rideId) {
        validateUserIsRequestedPassengerOrAdmin(passengerId);

        return passengerRateMapper.entityToDto(
                getPassengerRateEntity(passengerId, rideId));
    }

    /**
     * Creates a new Passenger rating with default values for rating.
     *
     * @param addingDto The PassengerRateAddingDto containing the data for creating the new rating.
     * @return A PassengerRateDto representing the newly created Passenger rating.
     * @throws com.cabaggregator.ratingservice.exception.DataUniquenessConflictException
     * if the provided rating record has been already created.
     */
    @Override
    @Transactional
    public PassengerRateDto savePassengerRate(PassengerRateAddingDto addingDto) {
        passengerRateValidator.validatePassengerRateUniqueness(addingDto.passengerId(), addingDto.rideId());

        PassengerRate newPassengerRate = passengerRateMapper.dtoToEntity(addingDto);

        return passengerRateMapper.entityToDto(
                passengerRateRepository.save(newPassengerRate));
    }

    /**
     * Sets a passenger rating for a specified ride.
     * This method is used by the Driver.
     *
     * @param passengerId The UUID of the Passenger being rated.
     * @param rideId The ObjectId of the ride for which the rating is being set.
     * @param settingDto The PassengerRateSettingDto containing the rating data to be set.
     * @return A PassengerRateDto representing the updated Passenger rating.
     * @throws ForbiddenException if the user is not participant of the ride.
     * @throws ResourceNotFoundException if the rating for the specified Passenger and ride does not exist.
     */
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
     * Validates that the current user is either the requested Passenger (resource owner) or an Admin.
     *
     * @param passengerId The UUID of the Passenger being accessed.
     * @throws ForbiddenException if the user is not authorized to access the ratings of the specified Passenger.
     */
    private void validateUserIsRequestedPassengerOrAdmin(UUID passengerId) {
        UUID userId = securityUtil.getUserIdFromSecurityContext();
        UserRole userRole = userRoleExtractor.extractCurrentUserRole();

        if (!userId.equals(passengerId) && userRole.equals(UserRole.PASSENGER)) {
            throw new ForbiddenException(ApplicationMessages.CANT_GET_RATES_OF_OTHER_USER);
        }
    }

    /**
     * Returns the existing Passenger rate for a specified Passenger and ride.
     * Throws an exception if the rate does not exist.
     *
     * @param passengerId The UUID of the Passenger whose rate is being retrieved.
     * @param rideId The ObjectId of the ride for which the rate is being retrieved.
     * @return A PassengerRate entity representing the rate for the specified Passenger and ride.
     * @throws ResourceNotFoundException if no rating is found for the specified Passenger and ride combination.
     */
    private PassengerRate getPassengerRateEntity(UUID passengerId, ObjectId rideId) {
        return passengerRateRepository
                .findByPassengerIdAndRideId(passengerId, rideId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_RATE_WITH_RIDE_ID_NOT_FOUND,
                        rideId));
    }
}
