package com.cabaggregator.ratingservice.util;

import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.entity.PassengerRate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PassengerRateTestUtil {
    public static final ObjectId ID = new ObjectId("5f49a10a5c1c0d1b3d54d7a4");
    public static final ObjectId RIDE_ID = new ObjectId("507f1f77bcf86cd799439011");
    public static final UUID DRIVER_ID = UUID.fromString("4665e57c-884a-433d-8fd2-55078f29eab9");
    public static final UUID PASSENGER_ID = UUID.fromString("1000e57c-114a-433d-6ac2-55048a29eab9");
    public static final Integer RATE = 4;

    public static final UUID OTHER_DRIVER_ID = UUID.fromString("b1f4e8b3-3b36-43c5-bc8e-7e7e3d2c1c27");

    public static final ObjectId NOT_EXISTING_RIDE_ID = new ObjectId("000000000000000000000000");
    public static final UUID NOT_EXISTING_PASSENGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static PassengerRate buildDefaultPassengerRate() {
        return PassengerRate.builder()
                .id(ID)
                .rideId(RIDE_ID)
                .driverId(DRIVER_ID)
                .passengerId(PASSENGER_ID)
                .rate(RATE)
                .build();
    }

    public static PassengerRateDto buildPassengerRateDto() {
        return new PassengerRateDto(
                ID,
                RIDE_ID,
                PASSENGER_ID,
                DRIVER_ID,
                RATE);
    }

    public static PassengerRateSettingDto buildPassengerRateSettingDto() {
        return new PassengerRateSettingDto(
                RATE);
    }

    public static PassengerRateAddingDto buildPassengerRateAddingDto() {
        return new PassengerRateAddingDto(
                RIDE_ID,
                PASSENGER_ID,
                DRIVER_ID);
    }
}
