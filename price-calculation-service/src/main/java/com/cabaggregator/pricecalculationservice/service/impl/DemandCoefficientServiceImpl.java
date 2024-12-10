package com.cabaggregator.pricecalculationservice.service.impl;

import com.cabaggregator.pricecalculationservice.entity.DemandCoefficient;
import com.cabaggregator.pricecalculationservice.exception.InternalErrorException;
import com.cabaggregator.pricecalculationservice.repository.DemandCoefficientRepository;
import com.cabaggregator.pricecalculationservice.service.DemandCacheService;
import com.cabaggregator.pricecalculationservice.service.DemandCoefficientService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandCoefficientServiceImpl implements DemandCoefficientService {
    private final DemandCoefficientRepository demandCoefficientRepository;

    private final DemandCacheService demandCache;

    public DemandCoefficient getCurrentDemandCoefficient(ObjectId rideId, String gridCell) {
        int demand = demandCache.getCellDemandFromCache(gridCell);

        String cachedRideId = demandCache.getRideFromCache(rideId.toString(), gridCell);

        if (cachedRideId == null) {
            increaseCellDemand(rideId, demand, gridCell);
        }

        return getDemandCoefficient(demand);
    }

    private void increaseCellDemand(ObjectId rideId, int demand, String gridCell) {
        demandCache.putRideIntoCache(rideId.toString(), gridCell);
        demandCache.putCellDemandIntoCache(demand + 1, gridCell);
    }

    private DemandCoefficient getDemandCoefficient(Integer currentOrders) {
        return demandCoefficientRepository
                .findHighestMatchingCoefficient(currentOrders)
                .orElseThrow(() -> new InternalErrorException(
                        String.format("no demand coefficient found for number of orders = %d", currentOrders)));
    }
}
