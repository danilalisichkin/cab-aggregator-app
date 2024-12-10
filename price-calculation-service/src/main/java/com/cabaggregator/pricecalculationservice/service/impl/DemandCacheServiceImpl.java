package com.cabaggregator.pricecalculationservice.service.impl;


import com.cabaggregator.pricecalculationservice.service.DemandCacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DemandCacheServiceImpl implements DemandCacheService {

    @Override
    @Cacheable(value = "demandCache", key = "#gridCell", unless = "#result == null")
    public int getCellDemandFromCache(String gridCell) {
        return 1;
    }

    @Override
    @CachePut(value = "demandCache", key = "#gridCell")
    public int putCellDemandIntoCache(int demand, String gridCell) {
        return demand;
    }

    @Override
    @Cacheable(value = "demandCache", key = "{#gridCell, #rideId}", unless = "#result == null")
    public String getRideFromCache(String rideId, String gridCell) {
        return null;
    }

    @Override
    @CachePut(value = "demandCache", key = "{#gridCell, #rideId}")
    public String putRideIntoCache(String rideId, String gridCell) {
        return rideId;
    }
}
