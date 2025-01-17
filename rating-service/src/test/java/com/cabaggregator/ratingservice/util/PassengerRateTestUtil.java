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
    public static final UUID DRIVER_ID = UUID.fromString("984b2c9d-8307-48ac-b5e3-e26d8fcb6c24");
    public static final UUID PASSENGER_ID = UUID.fromString("784aa16e-3d6d-4d28-b48e-2d4a0a4f49a6");
    public static final Integer RATE = 4;
    public static final Double AVERAGE_RATING = 3.5;

    public static final UUID OTHER_DRIVER_ID = UUID.fromString("15253953-8087-4983-b2c9-f2a9c2af1f31");
    public static final ObjectId OTHER_RIDE_ID = new ObjectId("30c8c7f7ece3c4a6c6c4e861");

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
