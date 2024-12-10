package com.cabaggregator.pricecalculationservice.service;

public interface DemandCacheService {
    int getCellDemandFromCache(String gridCell);

    int putCellDemandIntoCache(int demand, String gridCell);

    String getRideFromCache(String rideId, String gridCell);

    String putRideIntoCache(String rideId, String gridCell);
}
