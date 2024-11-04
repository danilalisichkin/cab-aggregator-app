package com.cabaggregator.passengerservice.service.impl;

import com.cabaggregator.passengerservice.PassengerTestUtil;
import com.cabaggregator.passengerservice.core.constant.PassengerFieldDefaultValues;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.mapper.PassengerMapper;
import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.exception.ResourceNotFoundException;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import com.cabaggregator.passengerservice.validator.PassengerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {
    @InjectMocks
    private PassengerService passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @Mock
    private PassengerValidator passengerValidator;

    private Passenger passenger;
    private PassengerDto passengerDto;
    private PassengerUpdatingDto passengerUpdatingDto;
    private PassengerAddingDto passengerAddingDto;

    @BeforeEach
    void setUp() {
        passenger = PassengerTestUtil.buildPassenger();
        passengerDto = PassengerTestUtil.buildPassengerDto();
        passengerUpdatingDto = PassengerTestUtil.buildPassengerUpdatingDto();
        passengerAddingDto = PassengerTestUtil.buildPassengerAddingDto();
    }

    @Test
    void getPassengerById_ShouldReturnPassenger_WhenPassengerFound() {
        Mockito.when(passengerRepository.findById(passenger.getId()))
                .thenReturn(Optional.of(passenger));
        Mockito.when(passengerMapper.entityToDto(passenger))
                .thenReturn(passengerDto);

        PassengerDto result = passengerService.getPassengerById(passenger.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(passengerDto);
    }

    @Test
    void getPassengerById_ShouldThrowResourceNotFoundException_WhenPassengerNotFound() {
        Mockito.when(passengerRepository.findById(passenger.getId()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> passengerService.getPassengerById(passenger.getId()));
    }

    @Test
    void updatePassenger_ShouldReturnPassenger_WhenPassengerFound() {
        Mockito.when(passengerRepository.findById(passenger.getId()))
                .thenReturn(Optional.of(passenger));
        Mockito.when(passengerMapper.updatingDtoToEntity(passengerUpdatingDto))
                .thenReturn(passenger);
        Mockito.when(passengerRepository.save(passenger))
                .thenReturn(passenger);
        Mockito.when(passengerMapper.entityToDto(passenger))
                .thenReturn(passengerDto);

        PassengerDto result = passengerService.updatePassenger(passenger.getId(), passengerUpdatingDto);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(passengerDto);
    }

    @Test
    void updatePassenger_ShouldThrowResourceNotFoundException_WhenPassengerNotFound() {
        final long idOfNotExistingPassenger = 100L;

        Mockito.when(passengerRepository.findById(idOfNotExistingPassenger))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> passengerService.updatePassenger(idOfNotExistingPassenger, passengerUpdatingDto));
    }

    @Test
    void savePassenger_ShouldReturnPassenger_WhenCreditsAreUnique() {
        final double expectedRating = PassengerFieldDefaultValues.DEFAULT_RATING;

        Mockito.doNothing().when(passengerValidator)
                .checkPhoneUniqueness(passengerAddingDto.phoneNumber());
        Mockito.doNothing().when(passengerValidator)
                .checkEmailUniqueness(passengerAddingDto.email());
        Mockito.when(passengerMapper.addingDtoToEntity(passengerAddingDto))
                .thenReturn(passenger);
        Mockito.when(passengerMapper.entityToDto(passenger))
                .thenReturn(passengerDto);
        Mockito.when(passengerRepository.save(passenger))
                .thenReturn(passenger);

        PassengerDto result = passengerService.savePassenger(passengerAddingDto);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(passengerDto);
        assertThat(result.rating()).isEqualTo(expectedRating);
    }
}
