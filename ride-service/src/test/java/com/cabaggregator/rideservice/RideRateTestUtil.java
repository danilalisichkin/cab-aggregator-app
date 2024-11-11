package com.cabaggregator.rideservice;

import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.entity.RideRate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideRateTestUtil {
    public static final ObjectId RIDE_RATE_ID = new ObjectId();
    public static final ObjectId RIDE_ID = RideTestUtil.RIDE_ID;
    public static final Integer PASSENGER_RATE = 5;
    public static final Integer DRIVER_RATE = 4;

    public static RideRate buildRideRate() {
        return RideRate.builder()
                .id(RIDE_RATE_ID)
                .rideId(RIDE_ID)
                .passengerRate(PASSENGER_RATE)
                .driverRate(DRIVER_RATE)
                .build();
    }

    public static RideRateDto buildRideRateDto() {
        return new RideRateDto(
                RIDE_RATE_ID,
                RIDE_ID,
                PASSENGER_RATE,
                DRIVER_RATE
        );
    }
}
