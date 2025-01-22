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
import com.cabaggregator.ratingservice.service.DriverRateService;
import com.cabaggregator.ratingservice.util.PageRequestBuilder;
import com.cabaggregator.ratingservice.validator.DriverRateValidator;
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
public class DriverRateServiceImpl implements DriverRateService {

    private final DriverRateRepository driverRateRepository;

    private final DriverRateMapper driverRateMapper;

    private final PageMapper pageMapper;

    private final DriverRateValidator driverRateValidator;

    /**
     * Returns the computed rating of a Driver.
     * The rating is the aggregated average rate value based on Driver ratings.
     *
     * @param driverId The UUID of the Driver whose rating is being retrieved.
     * @return The computed average rating of the Driver.
     * @throws ResourceNotFoundException if no rating is found for the specified Driver.
     */
    @Override
    public Double getDriverRating(UUID driverId) {
        return driverRateRepository
                .findAverageRateByDriverId(driverId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.DRIVER_RATING_NOT_FOUND));
    }

    /**
     * Returns a paginated list of Driver ratings.
     * This method is accessible by the Driver or an Admin.
     *
     * @param driverId  The UUID of the driver whose ratings are being retrieved.
     * @param offset    The page number for pagination (0-based).
     * @param limit     The maximum number of ratings per page.
     * @param sortBy    The field by which to sort the ratings.
     * @param sortOrder The direction of sorting (ascending or descending).
     * @return A PageDto containing a list of DriverRateDto objects representing the ratings.
     * @throws ForbiddenException if Driver tries to retrieve other Driver's rates.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN') or #driverId == authentication.principal")
    public PageDto<DriverRateDto> getPageOfDriverRates(
            UUID driverId, Integer offset, Integer limit, DriverRateSortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest = PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<DriverRate> driverRates = driverRateRepository.findAllByDriverId(driverId, pageRequest);

        return pageMapper.pageToPageDto(
                driverRateMapper.entityPageToDtoPage(driverRates));
    }

    /**
     * Returns a specific Driver's rate for a given ride.
     * This method is accessible by the Driver or an Admin.
     *
     * @param driverId The UUID of the Driver whose rating is being retrieved.
     * @param rideId   The ObjectId of the ride for which the rating is being fetched.
     * @return A DriverRateDto representing the rating of the driver for the specific ride.
     * @throws ResourceNotFoundException if no rating is found for the specified Driver and ride combination.
     * @throws ForbiddenException        if Driver tries to retrieve other Driver's rates.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN') or #driverId == authentication.principal")
    public DriverRateDto getDriverRate(UUID driverId, ObjectId rideId) {
        return driverRateMapper.entityToDto(
                getDriverRateEntity(driverId, rideId));
    }

    /**
     * Creates a new Driver rating with default values for rating and feedback.
     *
     * @param addingDto The DriverRateAddingDto containing the data for creating the new rating.
     * @return A DriverRateDto representing the newly created Driver rating.
     * @throws com.cabaggregator.ratingservice.exception.DataUniquenessConflictException if the provided rating record has been already created.
     */
    @Override
    @Transactional
    public DriverRateDto saveDriverRate(DriverRateAddingDto addingDto) {
        driverRateValidator.validateDriverRateUniqueness(addingDto.driverId(), addingDto.rideId());

        DriverRate newDriverRate = driverRateMapper.dtoToEntity(addingDto);

        return driverRateMapper.entityToDto(
                driverRateRepository.save(newDriverRate));
    }

    /**
     * Sets a driver rating and feedback for a specified ride.
     * This method is used by the Passenger.
     *
     * @param driverId   The UUID of the Driver being rated.
     * @param rideId     The ObjectId of the ride for which the rating is being set.
     * @param settingDto The DriverRateSettingDto containing the rating and feedback data to be set.
     * @return A DriverRateDto representing the updated Driver rating.
     * @throws ForbiddenException        if the user is not participant of the ride.
     * @throws ResourceNotFoundException if the rating for the specified Driver and ride does not exist.
     */
    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @driverRateValidator.isPassengerRideParticipant(#rideId, authentication.principal)")
    public DriverRateDto setDriverRate(UUID driverId, ObjectId rideId, DriverRateSettingDto settingDto) {
        DriverRate driverRateToUpdate = getDriverRateEntity(driverId, rideId);

        driverRateValidator.validateDriverRateSetting(driverRateToUpdate);

        driverRateMapper.updateEntityFromDto(settingDto, driverRateToUpdate);

        return driverRateMapper.entityToDto(
                driverRateRepository.save(driverRateToUpdate));
    }

    /**
     * Returns the existing Driver rate for a specified Driver and ride.
     * Throws an exception if the rate does not exist.
     *
     * @param driverId The UUID of the Driver whose rate is being retrieved.
     * @param rideId   The ObjectId of the ride for which the rate is being retrieved.
     * @return A DriverRate entity representing the rate for the specified Driver and ride.
     * @throws ResourceNotFoundException if no rating is found for the specified Driver and ride combination.
     */
    private DriverRate getDriverRateEntity(UUID driverId, ObjectId rideId) {
        return driverRateRepository
                .findByDriverIdAndRideId(driverId, rideId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_RATE_WITH_RIDE_ID_NOT_FOUND,
                        rideId));
    }
}
