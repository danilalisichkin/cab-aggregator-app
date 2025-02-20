package com.cabaggregator.ratingservice.util;

import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateSettingDto;
import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.entity.enums.FeedbackOption;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverRateTestUtil {
    public static final ObjectId ID = new ObjectId("5f47a08a5c1c0d1b2d56d7a5");
    public static final ObjectId RIDE_ID = new ObjectId("507f1f77bcf86cd799439011");
    public static final UUID DRIVER_ID = UUID.fromString("984b2c9d-8307-48ac-b5e3-e26d8fcb6c24");
    public static final UUID PASSENGER_ID = UUID.fromString("784aa16e-3d6d-4d28-b48e-2d4a0a4f49a6");
    public static final Integer RATE = 5;
    public static final Set<FeedbackOption> FEEDBACK_OPTIONS =
            Set.of(FeedbackOption.COMFORTABLE_RIDE, FeedbackOption.GOOD_MUSIC);
    public static final Double AVERAGE_RATING = 4.5;

    public static final UUID OTHER_PASSENGER_ID = UUID.fromString("30208072-8cfc-4289-aa9b-f5984ae2b807");
    public static final ObjectId OTHER_RIDE_ID = new ObjectId("30c8c7f7ece3c4a6c6c4e861");

    public static final ObjectId NOT_EXISTING_RIDE_ID = new ObjectId("000000000000000000000000");
    public static final UUID NOT_EXISTING_DRIVER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    public static DriverRate buildDefaultDriverRate() {
        return DriverRate.builder()
                .id(ID)
                .rideId(RIDE_ID)
                .driverId(DRIVER_ID)
                .passengerId(PASSENGER_ID)
                .rate(RATE)
                .feedbackOptions(FEEDBACK_OPTIONS)
                .build();
    }

    public static DriverRateDto buildDriverRateDto() {
        return new DriverRateDto(
                ID,
                RIDE_ID,
                DRIVER_ID,
                PASSENGER_ID,
                RATE,
                FEEDBACK_OPTIONS);
    }

    public static DriverRateSettingDto buildDriverRateSettingDto() {
        return new DriverRateSettingDto(
                RATE,
                FEEDBACK_OPTIONS);
    }

    public static DriverRateAddingDto buildDriverRateAddingDto() {
        return new DriverRateAddingDto(
                RIDE_ID,
                DRIVER_ID,
                PASSENGER_ID);
    }
}
