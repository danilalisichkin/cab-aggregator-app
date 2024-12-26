package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.client.PriceCalculationApiClient;
import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.client.dto.PriceResponse;
import com.cabaggregator.rideservice.client.dto.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.service.PromoCodeService;
import com.cabaggregator.rideservice.service.impl.PriceServiceImpl;
import com.cabaggregator.rideservice.util.PriceTestUtil;
import com.cabaggregator.rideservice.util.PromoCodeTestUtil;
import com.cabaggregator.rideservice.util.RideTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {
    @InjectMocks
    private PriceServiceImpl priceService;

    @Mock
    private PriceCalculationApiClient priceCalculationApiClient;

    @Mock
    private PromoCodeService promoCodeService;

    @Test
    void calculateBasePrice_ShouldCalculateBasePrice_WhenPriceCalculationServiceIsAvailable() {
        Ride actual = RideTestUtil.buildDefaultRide().toBuilder()
                .price(null)
                .build();
        RideAddingDto rideAddingDto = RideTestUtil.buildRideAddingDto();
        PriceResponse priceResponse = PriceTestUtil.buildPriceResponse();

        when(priceCalculationApiClient.calculatePrice(any(PriceCalculationRequest.class)))
                .thenReturn(priceResponse);

        priceService.calculateBasePrice(actual, rideAddingDto);

        assertThat(actual.getPrice())
                .isNotNull()
                .isEqualTo(priceResponse.getPrice());

        verify(priceCalculationApiClient).calculatePrice(any(PriceCalculationRequest.class));
    }

    @Test
    void recalculatePriceWithDiscount_ShouldRecalculatePriceWithDiscount_WhenPromoCodeServiceIsAvailable() {
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder()
                .promoCode(null)
                .build();
        long basePrice = ride.getPrice();
        RideAddingDto rideAddingDto = RideTestUtil.buildRideAddingDto();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        when(promoCodeService.getPromoCode(rideAddingDto.promoCode()))
                .thenReturn(promoCodeDto);
        doNothing().when(promoCodeService).createPromoStat(ride.getPassengerId(), promoCodeDto.value());

        priceService.recalculatePriceWithDiscount(ride, rideAddingDto);

        assertThat(ride.getPrice())
                .isNotNull()
                .isLessThan(basePrice);
        assertThat(ride.getPromoCode())
                .isNotNull()
                .isEqualTo(promoCodeDto.value());

        verify(promoCodeService).getPromoCode(rideAddingDto.promoCode());
        verify(promoCodeService).createPromoStat(ride.getPassengerId(), promoCodeDto.value());
    }
}
