package com.cabaggregator.passengerservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum PassengerSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    PHONE_NUMBER_ASC(Sort.by(Sort.Direction.ASC, "phoneNumber")),
    PHONE_NUMBER_DESC(Sort.by(Sort.Direction.DESC, "phoneNumber")),
    EMAIL_ASC(Sort.by(Sort.Direction.ASC, "email")),
    EMAIL_DESC(Sort.by(Sort.Direction.DESC, "email")),
    FIRST_NAME_ASC(Sort.by(Sort.Direction.ASC, "firstName")),
    FIRST_NAME_DESC(Sort.by(Sort.Direction.DESC, "firstName")),
    LAST_NAME_ASC(Sort.by(Sort.Direction.ASC, "lastName")),
    LAST_NAME_DESC(Sort.by(Sort.Direction.DESC, "lastName")),
    RATING_ASC(Sort.by(Sort.Direction.ASC, "rating")),
    RATING_DESC(Sort.by(Sort.Direction.DESC, "rating"));

    private final Sort sortValue;
}
