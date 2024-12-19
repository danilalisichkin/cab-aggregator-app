package com.cabaggregator.pricecalculationservice.service;

public interface GeoGridService {
    String calculateGridCell(double latitude, double longitude);
}
