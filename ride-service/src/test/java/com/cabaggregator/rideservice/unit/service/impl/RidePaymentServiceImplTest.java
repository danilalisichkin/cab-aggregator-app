package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.PaymentStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.mapper.RideMapper;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.security.util.SecurityUtil;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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

    @Mock
    private SecurityUtil securityUtil;

    @Test
    void changeRidePaymentStatus_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;
        UUID driverId = RideTestUtil.DRIVER_ID;
        PaymentStatus paymentStatus = PaymentStatus.PAID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> ridePaymentService.changeRidePaymentStatus(driverId, rideId, paymentStatus))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(securityUtil, rideValidator, rideMapper);
    }

    @Test
    void changeRidePaymentStatus_ShouldThrowForbiddenException_WhenUserTriesToGetRideOfOtherDriver() {
        Ride ride = RideTestUtil.buildDefaultRide();
        ObjectId rideId = ride.getId();
        UUID driverId = ride.getDriverId();
        PaymentStatus paymentStatus = PaymentStatus.PAID;
        UUID userId = RideTestUtil.NOT_EXISTING_DRIVER_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);

        assertThatThrownBy(
                () -> ridePaymentService.changeRidePaymentStatus(driverId, rideId, paymentStatus))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideValidator, rideMapper);
    }

    @Test
    void changeRidePaymentStatus_ShouldThrowForbiddenException_WhenDriverIsNotRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        ObjectId rideId = ride.getId();
        UUID driverId = RideTestUtil.NOT_EXISTING_DRIVER_ID;
        UUID userId = RideTestUtil.NOT_EXISTING_DRIVER_ID;
        PaymentStatus paymentStatus = PaymentStatus.PAID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doThrow(new ForbiddenException("error")).when(rideValidator).validateDriverParticipation(ride, userId);

        assertThatThrownBy(
                () -> ridePaymentService.changeRidePaymentStatus(driverId, rideId, paymentStatus))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validateDriverParticipation(ride, userId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void changeRidePaymentStatus_ShouldThrowForbiddenException_WhenDriverTriesToChangePaymentStatusForCardManually() {
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder().build();
        ObjectId rideId = ride.getId();
        UUID driverId = ride.getDriverId();
        PaymentStatus paymentStatus = PaymentStatus.DECLINED;
        UUID userId = ride.getDriverId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doNothing().when(rideValidator).validateDriverParticipation(ride, userId);

        assertThatThrownBy(
                () -> ridePaymentService.changeRidePaymentStatus(driverId, rideId, paymentStatus))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validateDriverParticipation(ride, userId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void changeRidePaymentStatus_ShouldChangeRidePaymentStatus_WhenCalledWithValidParameters() {
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder()
                .paymentMethod(PaymentMethod.CASH)
                .build();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();
        UUID driverId = ride.getDriverId();
        PaymentStatus paymentStatus = PaymentStatus.PAID_IN_CASH;
        UUID userId = ride.getDriverId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doNothing().when(rideValidator).validateDriverParticipation(ride, userId);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = ridePaymentService.changeRidePaymentStatus(driverId, rideId, paymentStatus);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validateDriverParticipation(ride, userId);
        verify(rideRepository).save(ride);
        verify(rideMapper).entityToDto(ride);
    }
}
