package com.cabaggregator.pricecalculationservice.service.impl;

import com.cabaggregator.pricecalculationservice.service.GeoGridService;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.cabaggregator.pricecalculationservice.config.GeoGridParams.CELL_SIZE;

@Service
public class GeoGridServiceImpl implements GeoGridService {

    /**
     * Calculates generalized geographic position from coordinates.
     * Used to combine multiple rides ordered from addresses close to each other.
     **/
    @Override
    public String calculateGridCell(double latitude, double longitude) {
        double roundedLat = Math.floor(latitude / CELL_SIZE) * CELL_SIZE;
        double roundedLon = Math.floor(longitude / CELL_SIZE) * CELL_SIZE;

        return String.format(Locale.US,"%.2f,%.2f", roundedLat, roundedLon);
    }
}
