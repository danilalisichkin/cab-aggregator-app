package com.cabaggregator.pricecalculationservice.service;

import com.cabaggregator.pricecalculationservice.entity.DemandCoefficient;
import org.bson.types.ObjectId;

public interface DemandCoefficientService {
    DemandCoefficient getCurrentDemandCoefficient(String gridCell, ObjectId rideId);
}
