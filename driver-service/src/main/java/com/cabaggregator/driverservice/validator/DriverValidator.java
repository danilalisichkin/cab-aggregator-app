package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constants.MessageKeys;
import com.cabaggregator.driverservice.dao.repository.CarRepository;
import com.cabaggregator.driverservice.dao.repository.DriverRepository;
import com.cabaggregator.driverservice.entities.Driver;
import com.cabaggregator.driverservice.exceptions.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DriverValidator {
    private final DriverRepository driverRepository;

    private final CarRepository carRepository;

    @Autowired
    public DriverValidator(DriverRepository driverRepository, CarRepository carRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    public void checkPhoneUniqueness(String phone) {
        if (driverRepository.existsByPhoneNumber(phone)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ERROR_DRIVER_WITH_PHONE_ALREADY_EXISTS,
                    phone);
        }
    }

    public void checkEmailUniqueness(String email) {
        if (driverRepository.existsByEmail(email)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ERROR_DRIVER_WITH_EMAIL_ALREADY_EXISTS,
                    email);
        }
    }

    public void checkExistenceOfOtherDriverWithSamePhone(Driver driver) {
        Optional<Driver> driverWithSamePhone = driverRepository.findByPhoneNumber(driver.getPhoneNumber());

        if (driverWithSamePhone.isPresent() && !driverWithSamePhone.get().equals(driver)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ERROR_DRIVER_WITH_PHONE_ALREADY_EXISTS,
                    driver.getPhoneNumber());
        }
    }

    public void checkExistenceOfOtherDriverWithSameEmail(Driver driver) {
        Optional<Driver> driverWithSameEmail = driverRepository.findByEmail(driver.getEmail());

        if (driverWithSameEmail.isPresent() && !driverWithSameEmail.get().equals(driver)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ERROR_DRIVER_WITH_EMAIL_ALREADY_EXISTS,
                    driver.getEmail());
        }
    }

    public void checkExistenceOfDriverWithId(long id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ERROR_DRIVER_WITH_ID_NOT_FOUND,
                    id);
        }
    }

    public void checkExistenceOfDriverCar(Long carId) {
        if (carId != null && !carRepository.existsById(carId)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ERROR_CAR_WITH_ID_NOT_FOUND,
                    carId);
        }
    }
}
