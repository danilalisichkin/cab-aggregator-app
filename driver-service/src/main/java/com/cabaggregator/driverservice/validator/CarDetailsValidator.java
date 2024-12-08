package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constant.ApplicationMessages;
import com.cabaggregator.driverservice.exception.ValidationErrorException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CarDetailsValidator {
    public void validateReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isAfter(LocalDate.now())) {
            throw new ValidationErrorException(ApplicationMessages.CAR_RELEASE_DATE_IS_AFTER_PRESENT);
        }
    }
}
