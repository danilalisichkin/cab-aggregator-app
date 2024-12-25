package com.cabaggregator.driverservice.util;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.entity.Driver;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverTestUtil {
    public static final UUID DRIVER_ID = UUID.fromString("4665e57c-884a-433d-8fd2-55078f29eab9");
    public static final String PHONE_NUMBER = "375291234567";
    public static final String EMAIL = "test@mail.com";
    public static final String FIRST_NAME = "Adam";
    public static final String LAST_NAME = "Smith";
    public static final Double RATING = 5.0;
    public static final Long CAR_ID = CarTestUtil.CAR_ID;

    public static final String UPDATED_PHONE_NUMBER = "375159988776";
    public static final String UPDATED_EMAIL = "new@mail.com";
    public static final String UPDATED_FIRST_NAME = "John";
    public static final String UPDATED_LAST_NAME = "Johnson";
    public static final Double UPDATED_RATING = 4.0;

    public static final UUID NOT_EXISTING_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String NOT_EXISTING_PHONE_NUMBER = "375251111111";
    public static final String NOT_EXISTING_EMAIL = "noexist@noexist.com";
    public static final Long NOT_EXISTING_CAR_ID = 0L;

    public static Driver.DriverBuilder getDriverBuilder() {
        return Driver.builder()
                .id(DRIVER_ID)
                .phoneNumber(PHONE_NUMBER)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .rating(RATING)
                .car(CarTestUtil.getCarBuilder().build());
    }

    public static DriverDto buildDriverDto() {
        return new DriverDto(
                DRIVER_ID,
                PHONE_NUMBER,
                EMAIL,
                FIRST_NAME,
                LAST_NAME,
                RATING,
                CAR_ID);
    }

    public static DriverUpdatingDto buildDriverUpdatingDto() {
        return new DriverUpdatingDto(
                UPDATED_PHONE_NUMBER,
                UPDATED_EMAIL,
                UPDATED_FIRST_NAME,
                UPDATED_LAST_NAME,
                UPDATED_RATING);
    }

    public static DriverAddingDto buildDriverAddingDto() {
        return new DriverAddingDto(
                DRIVER_ID,
                PHONE_NUMBER,
                EMAIL,
                FIRST_NAME,
                LAST_NAME);
    }
}
