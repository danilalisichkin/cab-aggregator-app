package com.cabaggregator.pricecalculationservice.unit.service.impl;

import com.cabaggregator.pricecalculationservice.service.GeoGridService;
import com.cabaggregator.pricecalculationservice.service.impl.GeoGridServiceImpl;
import com.cabaggregator.pricecalculationservice.util.GeoGridTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class GeoGridServiceImplTest {
    private final GeoGridService geoGridService = new GeoGridServiceImpl();

    @Test
    void calculateGeoGrid_ShouldReturnSimilarCell_WhenCoordinatesAreInOneCell() {
        String geoCell = geoGridService.calculateGridCell(
                GeoGridTestUtil.LATITUDE, GeoGridTestUtil.LONGITUDE);

        String otherGeoCell = geoGridService.calculateGridCell(
                GeoGridTestUtil.OTHER_CLOSE_LATITUDE, GeoGridTestUtil.OTHER_CLOSE_LONGITUDE);

        assertThat(geoCell)
                .isNotNull()
                .isEqualTo(otherGeoCell)
                .isEqualTo(GeoGridTestUtil.GRID_CELL);
    }
}
