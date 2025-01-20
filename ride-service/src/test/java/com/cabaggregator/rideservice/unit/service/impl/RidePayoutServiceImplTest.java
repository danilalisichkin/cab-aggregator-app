package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.config.PayoutPolicyConfig;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.kafka.dto.BalanceOperationRequest;
import com.cabaggregator.rideservice.kafka.producer.PayoutProducer;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.service.impl.RidePayoutServiceImpl;
import com.cabaggregator.rideservice.util.PayoutTestUtil;
import com.cabaggregator.rideservice.util.RideTestUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RidePayoutServiceImplTest {

    @InjectMocks
    private RidePayoutServiceImpl ridePayoutService;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private PayoutPolicyConfig payoutPolicyConfig;

    @Mock
    private PayoutProducer payoutProducer;

    @Test
    void createPayoutForRide_ShouldThrowResourceNotFoundException_WhenRideDoesNotExist() {
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> ridePayoutService.createPayoutForRide(rideId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoInteractions(payoutPolicyConfig, payoutProducer);
    }

    @Test
    void createPayoutForRide_ShouldSendMessageToKafkaTopic_WhenRideExists() {
        Ride ride = RideTestUtil.buildDefaultRide();
        ObjectId rideId = ride.getId();
        PayoutPolicyConfig.Policy policy = PayoutTestUtil.buildPayoutPolicy();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(payoutPolicyConfig.getComfort())
                .thenReturn(policy);
        doNothing().when(payoutProducer).sendMessage(any(BalanceOperationRequest.class));

        assertThatCode(
                () -> ridePayoutService.createPayoutForRide(rideId))
                .doesNotThrowAnyException();

        verify(rideRepository).findById(rideId);
        verify(payoutPolicyConfig).getComfort();
        verify(payoutProducer).sendMessage(any(BalanceOperationRequest.class));
    }
}
