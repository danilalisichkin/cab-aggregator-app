package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constant.ApplicationMessages;
import com.cabaggregator.driverservice.exception.BadRequestException;
import com.cabaggregator.driverservice.repository.CarDetailsRepository;
import com.cabaggregator.driverservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CarDetailsValidator {
    private final CarDetailsRepository carDetailsRepository;

    private final CarRepository carRepository;

    public void validateReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isAfter(LocalDate.now())) {
            throw new BadRequestException(ApplicationMessages.CAR_RELEASE_DATE_IS_AFTER_PRESENT);
        }
    }
}
