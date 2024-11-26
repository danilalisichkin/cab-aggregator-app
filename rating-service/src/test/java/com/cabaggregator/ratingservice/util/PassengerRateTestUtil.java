package com.cabaggregator.ratingservice.util;

import com.cabaggregator.ratingservice.entity.PassengerRate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PassengerRateTestUtil {
    public static final ObjectId ID = new ObjectId("5f47a08a5c1c0d1b2d56d7a4");
    public static final ObjectId RIDE_ID = new ObjectId("507f1f77bcf86cd799439011");
    public static final UUID DRIVER_ID = UUID.fromString("4665e57c-884a-433d-8fd2-55078f29eab9");
    public static final UUID PASSENGER_ID = UUID.fromString("1000e57c-114a-433d-6ac2-55048a29eab9");
    public static final Integer RATE = 4;

    public static PassengerRate.PassengerRateBuilder getPassengerRateBuilder() {
        return PassengerRate.builder()
                .id(ID)
                .rideId(RIDE_ID)
                .driverId(DRIVER_ID)
                .passengerId(PASSENGER_ID)
                .rate(RATE);
    }
}
