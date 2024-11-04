package com.cabaggregator.passengerservice.validator;

import com.cabaggregator.passengerservice.PassengerTestUtil;
import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.exception.DataUniquenessConflictException;
import com.cabaggregator.passengerservice.exception.ResourceNotFoundException;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
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
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class PassengerValidatorTest {
    @InjectMocks
    private PassengerValidator passengerValidator;

    @Mock
    private PassengerRepository passengerRepository;

    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = PassengerTestUtil.buildPassenger();
    }

    @Test
    void checkPhoneUniqueness_ShouldNotThrowDataUniquenessException_WhenPhoneIsUnique() {
        Mockito.when(passengerRepository.existsByPhoneNumber(PassengerTestUtil.PHONE_NUMBER))
                .thenReturn(false);

        assertThatCode(() -> passengerValidator.checkPhoneUniqueness(PassengerTestUtil.PHONE_NUMBER))
                .doesNotThrowAnyException();
    }

    @Test
    void checkPhoneUniqueness_ShouldThrowDataUniquenessException_WhenPhoneIsNotUnique() {
        Mockito.when(passengerRepository.existsByPhoneNumber(PassengerTestUtil.PHONE_NUMBER))
                .thenReturn(true);

        assertThatThrownBy(() -> passengerValidator.checkPhoneUniqueness(PassengerTestUtil.PHONE_NUMBER))
                .isInstanceOf(DataUniquenessConflictException.class);
    }

    @Test
    void checkEmailUniqueness_ShouldNotThrowDataUniquenessException_WhenEmailIsUnique() {
        Mockito.when(passengerRepository.existsByEmail(PassengerTestUtil.EMAIL))
                .thenReturn(false);

        assertThatCode(() -> passengerValidator.checkEmailUniqueness(PassengerTestUtil.EMAIL))
                .doesNotThrowAnyException();
    }

    @Test
    void checkEmailUniqueness_ShouldThrowDataUniquenessException_WhenEmailIsNotUnique() {
        Mockito.when(passengerRepository.existsByEmail(PassengerTestUtil.EMAIL))
                .thenReturn(true);

        assertThatThrownBy(() -> passengerValidator.checkEmailUniqueness(PassengerTestUtil.EMAIL))
                .isInstanceOf(DataUniquenessConflictException.class);
    }

    @Test
    void checkExistenceOfPassengerWithId_ShouldThrowResourceNotFoundException_WhenPassengerNotFound() {
        Mockito.when(passengerRepository.existsById(passenger.getId()))
                .thenReturn(false);

        assertThatThrownBy(() -> passengerValidator.checkExistenceOfPassengerWithId(passenger.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void checkExistenceOfPassengerWithId_ShouldNotThrowResourceNotFoundException_WhenPassengerFound() {
        Mockito.when(passengerRepository.existsById(passenger.getId()))
                .thenReturn(true);

        assertThatCode(() -> passengerValidator.checkExistenceOfPassengerWithId(passenger.getId()))
                .doesNotThrowAnyException();
    }
}
