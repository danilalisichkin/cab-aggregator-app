package com.cabaggregator.passengerservice.unit.validator;

import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.exception.DataUniquenessConflictException;
import com.cabaggregator.passengerservice.exception.ResourceNotFoundException;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import com.cabaggregator.passengerservice.util.PassengerTestUtil;
import com.cabaggregator.passengerservice.validator.PassengerValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PassengerValidatorTest {
    @InjectMocks
    private PassengerValidator passengerValidator;

    @Mock
    private PassengerRepository passengerRepository;

    @Test
    void checkPhoneUniqueness_ShouldNotThrowDataUniquenessException_WhenPhoneIsUnique() {
        Mockito.when(passengerRepository.existsByPhoneNumber(PassengerTestUtil.PHONE_NUMBER))
                .thenReturn(false);

        assertThatCode(() -> passengerValidator.validatePhoneUniqueness(PassengerTestUtil.PHONE_NUMBER))
                .doesNotThrowAnyException();
    }

    @Test
    void checkPhoneUniqueness_ShouldThrowDataUniquenessException_WhenPhoneIsNotUnique() {
        Mockito.when(passengerRepository.existsByPhoneNumber(PassengerTestUtil.PHONE_NUMBER))
                .thenReturn(true);

        assertThatThrownBy(() -> passengerValidator.validatePhoneUniqueness(PassengerTestUtil.PHONE_NUMBER))
                .isInstanceOf(DataUniquenessConflictException.class);
    }

    @Test
    void checkEmailUniqueness_ShouldNotThrowDataUniquenessException_WhenEmailIsUnique() {
        Mockito.when(passengerRepository.existsByEmail(PassengerTestUtil.EMAIL))
                .thenReturn(false);

        assertThatCode(() -> passengerValidator.validateEmailUniqueness(PassengerTestUtil.EMAIL))
                .doesNotThrowAnyException();
    }

    @Test
    void checkEmailUniqueness_ShouldThrowDataUniquenessException_WhenEmailIsNotUnique() {
        Mockito.when(passengerRepository.existsByEmail(PassengerTestUtil.EMAIL))
                .thenReturn(true);

        assertThatThrownBy(() -> passengerValidator.validateEmailUniqueness(PassengerTestUtil.EMAIL))
                .isInstanceOf(DataUniquenessConflictException.class);
    }

    @Test
    void checkExistenceOfPassengerWithId_ShouldThrowResourceNotFoundException_WhenPassengerNotFound() {
        Passenger passenger = PassengerTestUtil.getPassengerBuilder().build();

        Mockito.when(passengerRepository.existsById(passenger.getId()))
                .thenReturn(false);

        assertThatThrownBy(() -> passengerValidator.validateExistenceOfPassengerWithId(passenger.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void checkExistenceOfPassengerWithId_ShouldNotThrowResourceNotFoundException_WhenPassengerFound() {
        Passenger passenger = PassengerTestUtil.getPassengerBuilder().build();

        Mockito.when(passengerRepository.existsById(passenger.getId()))
                .thenReturn(true);

        assertThatCode(() -> passengerValidator.validateExistenceOfPassengerWithId(passenger.getId()))
                .doesNotThrowAnyException();
    }
}
