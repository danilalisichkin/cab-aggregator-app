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
    public static final Long CAR_ID = 2L;
    public static final String LICENSE_PLATE = "1234 AA-7";
    public static final String MAKE = "Ford";
    public static final String MODEL = "Focus";
    public static final String COLOR = "Blue";

    public static final String UPDATED_LICENSE_PLATE = "1111 AA-1";
    public static final String UPDATED_MAKE = "Porsche";
    public static final String UPDATED_MODEL = "Cayenne";
    public static final String UPDATED_COLOR = "Black";

    public static Car.CarBuilder getCarBuilder() {
        return Car.builder()
                .id(CAR_ID)
                .licensePlate(LICENSE_PLATE)
                .make(MAKE)
                .model(MODEL)
                .color(COLOR);
    }

    public static CarDto buildCarDto() {
        return new CarDto(
                CAR_ID,
                LICENSE_PLATE,
                MAKE,
                MODEL,
                COLOR);
    }

    public static CarFullDto buildCarFullDto() {
        return new CarFullDto(
                buildCarDto(),
                CarDetailsTestUtil.buildCarDetailsDto());
    }

    public static CarUpdatingDto buildCarUpdatingDto() {
        return new CarUpdatingDto(
                UPDATED_LICENSE_PLATE,
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
