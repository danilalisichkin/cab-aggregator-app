package com.cabaggregator.passengerservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PassengerSortField {
    ID("id"),
    PHONE_NUMBER("phoneNumber"),
    EMAIL("email"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    RATING("rating");

    private final String value;
}
