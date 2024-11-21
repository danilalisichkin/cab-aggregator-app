package com.cabaggregator.passengerservice;

import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.entity.Passenger;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class PassengerTestUtil {
    public static final UUID ID = UUID.fromString("1000e57c-114a-433d-6ac2-55048a29eab9");
    public static final String PHONE_NUMBER = "375291234567";
    public static final String EMAIL = "test@mail.com";
    public static final String FIRST_NAME = "testFirstName";
    public static final String LAST_NAME = "testLastName";
    public static final Double RATING = 5.0;

    public static final String UPDATED_PHONE_NUMBER = "375297654321";
    public static final String UPDATED_EMAIL = "test2@mail.com";
    public static final String UPDATED_FIRST_NAME = "test2FirstName";
    public static final String UPDATED_LAST_NAME = "test2LastName";
    public static final Double UPDATED_RATING = 2.0;

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
                UPDATED_PHONE_NUMBER,
                UPDATED_EMAIL,
                UPDATED_FIRST_NAME,
                UPDATED_LAST_NAME,
                UPDATED_RATING);
    }

    public static PassengerAddingDto buildPassengerAddingDto() {
        return new PassengerAddingDto(
                ID,
                PHONE_NUMBER,
                EMAIL,
                FIRST_NAME,
                LAST_NAME);
    }
}
