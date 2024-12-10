package com.cabaggregator.pricecalculationservice.unit.service.impl;

import com.cabaggregator.pricecalculationservice.entity.DemandCoefficient;
import com.cabaggregator.pricecalculationservice.repository.DemandCoefficientRepository;
import com.cabaggregator.pricecalculationservice.service.DemandCacheService;
import com.cabaggregator.pricecalculationservice.service.impl.DemandCoefficientServiceImpl;
import com.cabaggregator.pricecalculationservice.util.DemandCoefficientTestUtil;
import com.cabaggregator.pricecalculationservice.util.GeoGridTestUtil;
import com.cabaggregator.pricecalculationservice.util.PriceCalculationTestUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class DemandCoefficientServiceImplTest {
    @InjectMocks
    private DemandCoefficientServiceImpl demandCoefficientService;

    @Mock
    private DemandCoefficientRepository demandCoefficientRepository;

    @Mock
    private DemandCacheService demandCache;

    @Test
    void getCurrentDemandCoefficient_ShouldReturnDemandCoefficientAndIncreaseIt_WhenRideNotCachedAndCoefficientFound() {
        DemandCoefficient demandCoefficient = DemandCoefficientTestUtil.buildStandardDemandCoefficient();
        ObjectId rideId = PriceCalculationTestUtil.RIDE_ID;
        String gridCell = GeoGridTestUtil.GRID_CELL;
        int demand = DemandCoefficientTestUtil.CURRENT_ORDERS;

        when(demandCache.getCellDemandFromCache(gridCell))
                .thenReturn(demand);
        when(demandCache.getRideFromCache(rideId.toString(), gridCell))
                .thenReturn(null);
        when(demandCache.putRideIntoCache(rideId.toString(), gridCell))
                .thenReturn(rideId.toString());
        when(demandCache.putCellDemandIntoCache(demand + 1, gridCell))
                .thenReturn(demand);
        when(demandCoefficientRepository.findHighestMatchingCoefficient(demand))
                .thenReturn(Optional.of(demandCoefficient));

        DemandCoefficient actual = demandCoefficientService.getCurrentDemandCoefficient(rideId, gridCell);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(demandCoefficient);

        verify(demandCache).getCellDemandFromCache(gridCell);
        verify(demandCache).getRideFromCache(rideId.toString(), gridCell);
        verify(demandCache).putRideIntoCache(rideId.toString(), gridCell);
        verify(demandCache).putCellDemandIntoCache(demand + 1, gridCell);
        verify(demandCoefficientRepository).findHighestMatchingCoefficient(demand);
    }

    @Test
    void getCurrentDemandCoefficient_ShouldReturnDemandCoefficientAndNotIncreaseIt_WhenRideCachedAndCoefficientFound() {
        DemandCoefficient demandCoefficient = DemandCoefficientTestUtil.buildStandardDemandCoefficient();
        ObjectId rideId = PriceCalculationTestUtil.RIDE_ID;
        String gridCell = GeoGridTestUtil.GRID_CELL;
        int demand = DemandCoefficientTestUtil.CURRENT_ORDERS;

        when(demandCache.getCellDemandFromCache(gridCell))
                .thenReturn(demand);
        when(demandCache.getRideFromCache(rideId.toString(), gridCell))
                .thenReturn(rideId.toString());
        when(demandCoefficientRepository.findHighestMatchingCoefficient(demand))
                .thenReturn(Optional.of(demandCoefficient));

        DemandCoefficient actual = demandCoefficientService.getCurrentDemandCoefficient(rideId, gridCell);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(demandCoefficient);

        verify(demandCache).getCellDemandFromCache(gridCell);
        verify(demandCache).getRideFromCache(rideId.toString(), gridCell);
        verify(demandCache, never()).putRideIntoCache(rideId.toString(), gridCell);
        verify(demandCache, never()).putCellDemandIntoCache(demand + 1, gridCell);
        verify(demandCoefficientRepository).findHighestMatchingCoefficient(demand);
    }
}
