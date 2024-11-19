package com.cabaggregator.passengerservice.service.impl;

import com.cabaggregator.passengerservice.PassengerTestUtil;
import com.cabaggregator.passengerservice.core.constant.DefaultValues;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.mapper.PassengerMapper;
import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.exception.ResourceNotFoundException;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import com.cabaggregator.passengerservice.validator.PassengerValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {
    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @Mock
    private PassengerValidator passengerValidator;

    @Test
    void getPassengerById_ShouldReturnPassenger_WhenPassengerFound() {
        Passenger passenger = PassengerTestUtil.buildPassenger();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();

        Mockito.when(passengerRepository.findById(passenger.getId()))
                .thenReturn(Optional.of(passenger));
        Mockito.when(passengerMapper.entityToDto(passenger))
                .thenReturn(passengerDto);

        PassengerDto result = passengerService.getPassengerById(passenger.getId());

        assertThat(result)
                .isNotNull()
                .isEqualTo(passengerDto);
    }

    @Test
    void getPassengerById_ShouldThrowResourceNotFoundException_WhenPassengerNotFound() {
        Passenger passenger = PassengerTestUtil.buildPassenger();

        Mockito.when(passengerRepository.findById(passenger.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> passengerService.getPassengerById(passenger.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updatePassenger_ShouldReturnPassenger_WhenPassengerFound() {
        Passenger passenger = PassengerTestUtil.buildPassenger();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();
        PassengerUpdatingDto passengerUpdatingDto = PassengerTestUtil.buildPassengerUpdatingDto();

        Mockito.when(passengerRepository.findById(passenger.getId()))
                .thenReturn(Optional.of(passenger));
        Mockito.when(passengerRepository.save(passenger))
                .thenReturn(passenger);
        Mockito.when(passengerMapper.entityToDto(passenger))
                .thenReturn(passengerDto);

        PassengerDto result = passengerService.updatePassenger(passenger.getId(), passengerUpdatingDto);

        assertThat(result)
                .isNotNull()
                .isEqualTo(passengerDto);
    }

    @Test
    void updatePassenger_ShouldThrowResourceNotFoundException_WhenPassengerNotFound() {
        PassengerUpdatingDto passengerUpdatingDto = PassengerTestUtil.buildPassengerUpdatingDto();

        final String idOfNotExistingPassenger = "1000e57c-114a-433d-6ac2-55048a29eab9";

        Mockito.when(passengerRepository.findById(idOfNotExistingPassenger))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> passengerService.updatePassenger(idOfNotExistingPassenger, passengerUpdatingDto));
    }

    @Test
    void savePassenger_ShouldReturnPassenger_WhenCreditsAreUnique() {
        Passenger passenger = PassengerTestUtil.buildPassenger();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();
        PassengerAddingDto passengerAddingDto = PassengerTestUtil.buildPassengerAddingDto();

        final Double expectedRating = DefaultValues.DEFAULT_RATING;

        Mockito.doNothing().when(passengerValidator)
                .validatePhoneUniqueness(passengerAddingDto.phoneNumber());
        Mockito.doNothing().when(passengerValidator)
                .validateEmailUniqueness(passengerAddingDto.email());
        Mockito.when(passengerMapper.dtoToEntity(passengerAddingDto))
                .thenReturn(passenger);
        Mockito.when(passengerMapper.entityToDto(passenger))
                .thenReturn(passengerDto);
        Mockito.when(passengerRepository.save(passenger))
                .thenReturn(passenger);

        PassengerDto result = passengerService.savePassenger(passengerAddingDto);

        assertThat(result)
                .isNotNull()
                .isEqualTo(passengerDto);
        assertThat(result.rating()).isEqualTo(expectedRating);
    }
}
