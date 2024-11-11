package com.cabaggregator.rideservice.entity.enums;

import com.cabaggregator.rideservice.entity.conveter.ServiceCategoryConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = ServiceCategoryConverter.Serializer.class)
@JsonDeserialize(using = ServiceCategoryConverter.Deserializer.class)
public enum ServiceCategory {
    ECONOMY(1, "ECONOMY"),
    COMFORT(2, "COMFORT"),
    BUSINESS(3, "BUSINESS");

    private final int id;
    private final String value;

    public static ServiceCategory getById(int id) {
        return Arrays.stream(ServiceCategory.values())
                .filter(serviceCategory -> serviceCategory.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown id: " + id));
    }
}
