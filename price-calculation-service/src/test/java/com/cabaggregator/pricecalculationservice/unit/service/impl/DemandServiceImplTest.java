package com.cabaggregator.pricecalculationservice.unit.service.impl;

import com.cabaggregator.pricecalculationservice.core.constant.DemandSettings;
import com.cabaggregator.pricecalculationservice.repository.CellDemandStore;
import com.cabaggregator.pricecalculationservice.repository.CellRideStore;
import com.cabaggregator.pricecalculationservice.service.impl.DemandServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class DemandServiceImplTest {
    @InjectMocks
    private DemandServiceImpl demandService;

    @Mock
    private CellDemandStore cellDemandStore;

    @Mock
    private CellRideStore cellRideStore;

    @Test
    void calculateCurrentDemandForRide_ShouldSetDefaultDemandAndReturnIt_WhenCellDemandNotSetYet() {
        String gridCell = GeoGridTestUtil.GRID_CELL;
        ObjectId rideId = PriceCalculationTestUtil.RIDE_ID;

        when(cellDemandStore.get(gridCell))
                .thenReturn(Optional.empty());
        when(cellRideStore.exists(gridCell, rideId))
                .thenReturn(anyBoolean());

        int actual = demandService.calculateCurrentDemandForRide(gridCell, rideId);

        assertThat(actual).isEqualTo(DemandSettings.INITIAL_DEMAND);

        verify(cellDemandStore).get(gridCell);
        verify(cellRideStore).exists(gridCell, rideId);
        verify(cellDemandStore).set(gridCell, DemandSettings.INITIAL_DEMAND);
        verify(cellRideStore).set(gridCell, rideId);
        verify(cellDemandStore, never()).increment(gridCell);
    }

    @Test
    void calculateCurrentDemandForRide_ShouldReturnCurrentDemandAndNotIncreaseIt_WhenRideIsAlreadyCounted() {
        Integer currentOrders = DemandCoefficientTestUtil.CURRENT_ORDERS;
        String gridCell = GeoGridTestUtil.GRID_CELL;
        ObjectId rideId = PriceCalculationTestUtil.RIDE_ID;


        when(cellDemandStore.get(gridCell))
                .thenReturn(Optional.of(currentOrders));
        when(cellRideStore.exists(gridCell, rideId))
                .thenReturn(true);

        int actual = demandService.calculateCurrentDemandForRide(gridCell, rideId);

        assertThat(actual).isEqualTo(currentOrders);

        verify(cellDemandStore).get(gridCell);
        verify(cellRideStore).exists(gridCell, rideId);
        verify(cellDemandStore, never()).increment(gridCell);
        verify(cellRideStore, never()).set(gridCell, rideId);
        verify(cellDemandStore, never()).set(gridCell, DemandSettings.INITIAL_DEMAND);
        verify(cellRideStore, never()).set(gridCell, rideId);
    }

    @Test
    void calculateCurrentDemandForRide_ShouldReturnCurrentDemandAndIncreaseIt_WhenRideNotCountedYet() {
        Integer currentOrders = DemandCoefficientTestUtil.CURRENT_ORDERS;
        String gridCell = GeoGridTestUtil.GRID_CELL;
        ObjectId rideId = PriceCalculationTestUtil.RIDE_ID;

        when(cellDemandStore.get(gridCell))
                .thenReturn(Optional.of(currentOrders));
        when(cellRideStore.exists(gridCell, rideId))
                .thenReturn(false);

        int actual = demandService.calculateCurrentDemandForRide(gridCell, rideId);

        assertThat(actual).isEqualTo(currentOrders);

        verify(cellDemandStore).get(gridCell);
        verify(cellRideStore).exists(gridCell, rideId);
        verify(cellDemandStore).increment(gridCell);
        verify(cellRideStore).set(gridCell, rideId);
        verify(cellDemandStore, never()).set(gridCell, DemandSettings.INITIAL_DEMAND);
    }
}
