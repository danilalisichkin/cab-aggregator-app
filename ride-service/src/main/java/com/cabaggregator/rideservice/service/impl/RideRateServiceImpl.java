package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.enums.UserRole;
import com.cabaggregator.rideservice.core.mapper.RideRateMapper;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.entity.RideRate;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.RideRateRepository;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.service.RideRateService;
import com.cabaggregator.rideservice.validator.RideRateValidator;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RideRateServiceImpl implements RideRateService {
    private final RideRateRepository rideRateRepository;
    private final RideRepository rideRepository;

    private final RideRateMapper rideRateMapper;

    private final RideRateValidator rideRateValidator;

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
    public List<RideRateDto> getListOfRideRates(String passengerId, String driverId) {
        rideRateValidator.validateProvidedParameters(passengerId, driverId);

        List<Ride> rides;
        if (passengerId != null && driverId != null) {
            rides = rideRepository.findByPassengerIdAndDriverId(passengerId, driverId);
        } else if (driverId != null) {
            rides = rideRepository.findByDriverId(driverId);
        } else {
            rides = rideRepository.findByPassengerId(passengerId);
        }

        return rides.stream()
                .map(ride -> getRideRateByRideId(ride.getId()))
                .toList();
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
