package com.cabaggregator.driverservice.util;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.entity.Car;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CarTestUtil {
    public static final Long ID = 1L;
    public static final String LICENSE_PLATE = "1234 AA-7";
    public static final String MAKE = "Ford";
    public static final String MODEL = "Focus";
    public static final String COLOR = "Blue";

    public static final String UPDATED_LICENSE_PLATE = "1111 AA-1";
    public static final String UPDATED_MAKE = "Porsche";
    public static final String UPDATED_MODEL = "Cayenne";
    public static final String UPDATED_COLOR = "Black";

    public static final Long OTHER_CAR_ID = 2L;
    public static final String OTHER_LICENSE_PLATE = "7777 KK-7";

    public static final Long FREE_CAR_ID = 3L;

    public static final Long NOT_EXISTING_ID = 4L;
    public static final String NOT_EXISTING_LICENSE_PLATE = "0000 OO-1";

    public static Car buildDefaultCar() {
        return Car.builder()
                .id(ID)
                .licensePlate(LICENSE_PLATE)
                .make(MAKE)
                .model(MODEL)
                .color(COLOR)
                .build();
    }

    public static CarDto buildCarDto() {
        return new CarDto(
                ID,
                LICENSE_PLATE,
                MAKE,
                MODEL,
                COLOR);
    }

    public static CarDto buildUpdatedCarDto() {
        return new CarDto(
                ID,
                UPDATED_LICENSE_PLATE,
                UPDATED_MAKE,
                UPDATED_MODEL,
                UPDATED_COLOR);
    }

    public static CarFullDto buildCarFullDto() {
        return new CarFullDto(
                buildCarDto(),
                CarDetailsTestUtil.buildCarDetailsDto());
    }

    public static CarFullDto buildUpdatedCarFullDto() {
        return new CarFullDto(
                buildCarDto(),
                CarDetailsTestUtil.buildUpdateCarDetailsDto());
    }

    public static CarUpdatingDto buildCarUpdatingDto() {
        return new CarUpdatingDto(
                UPDATED_LICENSE_PLATE,
                UPDATED_MAKE,
                UPDATED_MODEL,
                UPDATED_COLOR);
    }

    public static CarUpdatingDto buildConflictCarUpdatingDto() {
        return new CarUpdatingDto(
                OTHER_LICENSE_PLATE,
                UPDATED_MAKE,
                UPDATED_MODEL,
                UPDATED_COLOR);
    }

    public static CarAddingDto buildCarAddingDto() {
        return new CarAddingDto(
                LICENSE_PLATE,
                MAKE,
                MODEL,
                COLOR,
                CarDetailsTestUtil.buildCarDetailsSettingDto());
    }
}
