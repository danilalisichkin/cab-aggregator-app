package com.cabaggregator.driverservice;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.entity.CarDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CarDetailsTestUtil {
    public static final Long DETAILS_ID = 1L;
    public static final LocalDate RELEASE_DATE = LocalDate.of(2024, 1, 1);
    public static final Integer SEAT_CAPACITY = 5;

    public static final LocalDate UPDATED_RELEASE_DATE = LocalDate.of(2014, 1, 1);
    public static final Integer UPDATED_SEAT_CAPACITY = 4;

    public static CarDetails buildCarDetails() {
        return CarDetails.builder()
                .id(DETAILS_ID)
                .releaseDate(RELEASE_DATE)
                .seatCapacity(SEAT_CAPACITY)
                .car(CarTestUtil.buildCar())
                .build();
    }

    public static CarDetailsDto buildCarDetailsDto() {
        return new CarDetailsDto(
                DETAILS_ID,
                RELEASE_DATE,
                SEAT_CAPACITY);
    }

    public static CarDetailsSettingDto buildCarDetailsSettingDto() {
        return new CarDetailsSettingDto(
                UPDATED_RELEASE_DATE,
                UPDATED_SEAT_CAPACITY);
    }
}
