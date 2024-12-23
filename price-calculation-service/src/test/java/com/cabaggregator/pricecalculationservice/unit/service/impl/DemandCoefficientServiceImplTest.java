package com.cabaggregator.pricecalculationservice.unit.service.impl;

import com.cabaggregator.pricecalculationservice.entity.DemandCoefficient;
import com.cabaggregator.pricecalculationservice.repository.DemandCoefficientRepository;
import com.cabaggregator.pricecalculationservice.service.DemandService;
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
    private DemandService demandService;

    @Test
    void getCurrentDemandCoefficient_ShouldReturnDemandCoefficient_WhenCalledWithValidParams() {
        DemandCoefficient demandCoefficient = DemandCoefficientTestUtil.buildStandardDemandCoefficient();
        ObjectId rideId = PriceCalculationTestUtil.RIDE_ID;
        String gridCell = GeoGridTestUtil.GRID_CELL;
        int demand = DemandCoefficientTestUtil.CURRENT_ORDERS;

        when(demandService.calculateCurrentDemandForRide(gridCell, rideId))
                .thenReturn(demand);
        when(demandCoefficientRepository.findHighestMatchingCoefficient(demand))
                .thenReturn(Optional.of(demandCoefficient));

        DemandCoefficient actual = demandCoefficientService.getCurrentDemandCoefficient(gridCell, rideId);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(demandCoefficient);

        verify(demandService).calculateCurrentDemandForRide(gridCell, rideId);
        verify(demandCoefficientRepository).findHighestMatchingCoefficient(demand);
    }
}
