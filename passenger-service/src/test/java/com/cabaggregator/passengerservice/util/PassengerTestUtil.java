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
    public static final UUID ID = UUID.fromString("784aa16e-3d6d-4d28-b48e-2d4a0a4f49a6");
    public static final String PHONE_NUMBER = "375293333333";
    public static final String EMAIL = "passenger@gmail.com";
    public static final String FIRST_NAME = "Frank";
    public static final String LAST_NAME = "Ocean";

    public static final UUID OTHER_ID = UUID.fromString("30208072-8cfc-4289-aa9b-f5984ae2b807");
    public static final String OTHER_PHONE_NUMBER = "375293344556";

    public static final UUID NOT_EXISTING_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String NOT_EXISTING_PHONE_NUMBER = "375441111111";
    public static final String NOT_EXISTING_EMAIL = "notexisting@mail.com";

    public static final String UPDATED_PHONE_NUMBER = "375297654321";
    public static final String UPDATED_EMAIL = "newmail@mail.com";
    public static final String UPDATED_FIRST_NAME = "NewFirstName";
    public static final String UPDATED_LAST_NAME = "NewLastName";

    public static Passenger buildDefaultPassenger() {
        return Passenger.builder()
                .id(ID)
                .phoneNumber(PHONE_NUMBER)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static PassengerDto buildPassengerDto() {
        return new PassengerDto(
                ID,
                PHONE_NUMBER,
                EMAIL,
                FIRST_NAME,
                LAST_NAME);
    }

    public static PassengerDto buildUpdatedPassengerDto() {
        return new PassengerDto(
                ID,
                UPDATED_PHONE_NUMBER,
                UPDATED_EMAIL,
                UPDATED_FIRST_NAME,
                UPDATED_LAST_NAME);
    }

    public static PassengerUpdatingDto buildPassengerUpdatingDto() {
        return new PassengerUpdatingDto(
                UPDATED_PHONE_NUMBER,
                UPDATED_EMAIL,
                UPDATED_FIRST_NAME,
                UPDATED_LAST_NAME);
    }

    public static PassengerUpdatingDto buildConflictPassengerUpdatingDto() {
        return new PassengerUpdatingDto(
                OTHER_PHONE_NUMBER,
                UPDATED_EMAIL,
                UPDATED_FIRST_NAME,
                UPDATED_LAST_NAME);
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
