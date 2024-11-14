package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.enums.UserRole;
import com.cabaggregator.rideservice.core.mapper.RideRateMapper;
import com.cabaggregator.rideservice.entity.RideRate;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.RideRateRepository;
import com.cabaggregator.rideservice.service.RideRateService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RideRateServiceImpl implements RideRateService {
    private final RideRateRepository rideRateRepository;
    private final RideRateMapper rideRateMapper;

    @Override
    public RideRateDto getRideRateById(ObjectId id) {
        return rideRateMapper.entityToDto(
                getRideRateEntityById(id));
    }

    @Override
    public RideRateDto getRideRateByRideId(ObjectId rideId) {
        return rideRateMapper.entityToDto(
                getRideRateEntityByRideId(rideId));
    }

    @Override
    @Transactional
    public RideRateDto saveRideRate(ObjectId rideId, UserRole role, Integer rate) {

        RideRate rideRate =
                rideRateRepository.findByRideId(rideId)
                        .orElse(new RideRate());

        setRideRateAccordingToRole(rideRate, role, rate);

        return rideRateMapper.entityToDto(
                rideRateRepository.save(rideRate));
    }

    private RideRate getRideRateEntityById(ObjectId id) {
        return rideRateRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_RATE_WITH_ID_NOT_FOUND,
                        id));
    }

    private RideRate getRideRateEntityByRideId(ObjectId rideId) {
        return rideRateRepository
                .findByRideId(rideId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_RATE_WITH_RIDE_ID_NOT_FOUND,
                        rideId));
    }

    private void setRideRateAccordingToRole(RideRate rideRate, UserRole role, Integer rate) {
        if (role == UserRole.DRIVER) {
            if (rideRate.getPassengerRate() != null) {
                throw new BadRequestException(ApplicationMessages.RIDE_RATE_ALREADY_SET);
            }
            rideRate.setPassengerRate(rate);
        } else if (role == UserRole.PASSENGER) {
            if (rideRate.getDriverRate() != null) {
                throw new BadRequestException(ApplicationMessages.RIDE_RATE_ALREADY_SET);
            }
            rideRate.setDriverRate(rate);
        }
    }
}
