package com.cabaggregator.passengerservice;

import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.entity.Passenger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PassengerTestUtil {
    public static final long ID = 1L;
    public static final String PHONE_NUMBER = "375291234567";
    public static final String EMAIL = "test@mail.com";
    public static final String FIRST_NAME = "testFirstName";
    public static final String LAST_NAME = "testLastName";
    public static final double RATING = 5.0;

    public static Passenger buildPassenger() {
        return Passenger.builder()
                .id(ID)
                .phoneNumber(PHONE_NUMBER)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .rating(RATING)
                .build();
    }

    public static PassengerDto buildPassengerDto() {
        return new PassengerDto(
                ID,
                PHONE_NUMBER,
                EMAIL,
                FIRST_NAME,
                LAST_NAME,
                RATING);
    }

    public static PassengerUpdatingDto buildPassengerUpdatingDto() {
        return new PassengerUpdatingDto(
                PHONE_NUMBER,
                EMAIL,
                FIRST_NAME,
                LAST_NAME,
                RATING);
    }

    public static PassengerAddingDto buildPassengerAddingDto() {
        return new PassengerAddingDto(
                PHONE_NUMBER,
                EMAIL,
                FIRST_NAME,
                LAST_NAME);
    }
}
