package com.cabaggregator.rideservice.validator;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.repository.RideRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RideRateValidator {
    private final RideRateRepository rideRateRepository;

    public void validateProvidedParameters(String passengerId, String driverId) {
        if (passengerId == null && driverId == null) {
            throw new BadRequestException(
                    ValidationErrors.NO_PARAMETERS_PROVIDED);
        }
    }
}
