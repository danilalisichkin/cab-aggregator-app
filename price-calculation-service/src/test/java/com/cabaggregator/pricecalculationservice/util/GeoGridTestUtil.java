package com.cabaggregator.pricecalculationservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeoGridTestUtil {
    public static final double LONGITUDE = -118.238512;
    public static final double LATITUDE = 34.05756;
    public static final List<Double> COORDINATES = List.of(LONGITUDE, LATITUDE);
    public static final String GRID_CELL = "34.05,-118.24";

    public static final double OTHER_CLOSE_LONGITUDE = -118.231354;
    public static final double OTHER_CLOSE_LATITUDE = 34.05256;
    public static final List<Double> OTHER_CLOSE_COORDINATES = List.of(OTHER_CLOSE_LONGITUDE, OTHER_CLOSE_LATITUDE);
}
