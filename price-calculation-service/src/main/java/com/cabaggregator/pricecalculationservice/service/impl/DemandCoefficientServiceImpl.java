package com.cabaggregator.pricecalculationservice.service.impl;

import com.cabaggregator.pricecalculationservice.entity.DemandCoefficient;
import com.cabaggregator.pricecalculationservice.exception.InternalErrorException;
import com.cabaggregator.pricecalculationservice.repository.DemandCoefficientRepository;
import com.cabaggregator.pricecalculationservice.service.DemandCoefficientService;
import com.cabaggregator.pricecalculationservice.service.DemandService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandCoefficientServiceImpl implements DemandCoefficientService {

    private final DemandCoefficientRepository demandCoefficientRepository;

    private final DemandService demandService;

    /**
     * Retrieves current demand coefficient for ride from specified grid cell.
     * Does not cache result because demand changes with every gained request.
     **/
    @Override
    public DemandCoefficient getCurrentDemandCoefficient(String gridCell, ObjectId rideId) {
        Integer currentCellDemand = demandService.calculateCurrentDemandForRide(gridCell, rideId);

        return getDemandCoefficient(currentCellDemand);
    }

    private DemandCoefficient getDemandCoefficient(Integer currentDemand) {
        return demandCoefficientRepository
                .findHighestMatchingCoefficient(currentDemand)
                .orElseThrow(() -> new InternalErrorException(
                        String.format("no demand coefficient found for number of orders = %d", currentDemand)));
    }
}
