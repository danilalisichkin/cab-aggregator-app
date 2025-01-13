package com.cabaggregator.passengerservice.util;

import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.entity.Passenger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PassengerTestUtil {
    public static final UUID ID = UUID.fromString("1000e57c-114a-433d-6ac2-55048a29eab9");
    public static final String PHONE_NUMBER = "375291234567";
    public static final String EMAIL = "test@mail.com";
    public static final String FIRST_NAME = "Adam";
    public static final String LAST_NAME = "Smith";
    public static final Double RATING = 5.0;

    public static final UUID NOT_EXISTING_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String NOT_EXISTING_PHONE_NUMBER = "375441111111";
    public static final String NOT_EXISTING_EMAIL = "notexisting@mail.com";

    public static final String UPDATED_PHONE_NUMBER = "375297654321";
    public static final String UPDATED_EMAIL = "newmail@mail.com";
    public static final String UPDATED_FIRST_NAME = "NewFirstName";
    public static final String UPDATED_LAST_NAME = "NewLastName";
    public static final Double UPDATED_RATING = 2.0;

    public static Passenger buildDefaultPassenger() {
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
