package com.cabaggregator.pricecalculationservice.service.impl;

import com.cabaggregator.pricecalculationservice.core.constant.DemandSettings;
import com.cabaggregator.pricecalculationservice.repository.CellDemandStore;
import com.cabaggregator.pricecalculationservice.repository.CellRideStore;
import com.cabaggregator.pricecalculationservice.service.DemandService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandServiceImpl implements DemandService {
    private final CellDemandStore cellDemandStore;

    private final CellRideStore cellRideStore;

    /**
     * Calculates current demand for ride in grid cell, if it's not found - set it as default.
     * If ride was not counted in grid cell, adds ride to cell and increase demand.
     **/
    public int calculateCurrentDemandForRide(String gridCell, ObjectId rideId) {
        Optional<Integer> currentCellDemand = cellDemandStore.get(gridCell);
        boolean isCellDemandAlreadySet = currentCellDemand.isPresent();
        boolean isRideAlreadyCounted = cellRideStore.exists(gridCell, rideId);

        if (isCellDemandAlreadySet) {
            if (!isRideAlreadyCounted) {
                cellDemandStore.increment(gridCell);
                cellRideStore.set(gridCell, rideId);
            }

            return currentCellDemand.get();
        } else {
            cellDemandStore.set(gridCell, DemandSettings.INITIAL_DEMAND);
            cellRideStore.set(gridCell, rideId);

            return DemandSettings.INITIAL_DEMAND;
        }
    }
}
