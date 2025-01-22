package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.core.mapper.RideMapper;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.service.impl.RidePaymentServiceImpl;
import com.cabaggregator.rideservice.util.RideTestUtil;
import com.cabaggregator.rideservice.validator.RideValidator;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RidePaymentServiceImplTest {
    @InjectMocks
    private RidePaymentServiceImpl ridePaymentService;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideValidator rideValidator;

    @Mock
    private RideMapper rideMapper;

    @Test
    void changeRidePaymentStatus_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;
        UUID driverId = RideTestUtil.DRIVER_ID;
        RidePaymentStatus paymentStatus = RidePaymentStatus.PAID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> ridePaymentService.changeRidePaymentStatus(driverId, rideId, paymentStatus))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideValidator, rideMapper);
    }

    @Test
    void changeRidePaymentStatus_ShouldChangeRidePaymentStatus_WhenCalledWithValidParameters() {
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder()
                .paymentMethod(PaymentMethod.CASH)
                .build();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();
        UUID driverId = ride.getDriverId();
        RidePaymentStatus paymentStatus = RidePaymentStatus.PAID_IN_CASH;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = ridePaymentService.changeRidePaymentStatus(driverId, rideId, paymentStatus);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
        verify(rideRepository).save(ride);
        verify(rideMapper).entityToDto(ride);
    }
}
