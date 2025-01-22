package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.client.PriceCalculationApiClient;
import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.client.dto.PriceRecalculationDto;
import com.cabaggregator.rideservice.client.dto.PriceResponse;
import com.cabaggregator.rideservice.client.dto.PromoCodeDto;
import com.cabaggregator.rideservice.service.PromoCodeService;
import com.cabaggregator.rideservice.service.impl.PriceServiceImpl;
import com.cabaggregator.rideservice.util.PriceTestUtil;
import com.cabaggregator.rideservice.util.PromoCodeTestUtil;
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
        PriceCalculationRequest calculationRequest = PriceTestUtil.buildPriceCalculationRequest();
        PriceResponse priceResponse = PriceTestUtil.buildPriceResponse();

        when(priceCalculationApiClient.calculatePrice(any(PriceCalculationRequest.class)))
                .thenReturn(priceResponse);

        Long actual = priceService.calculateBasePrice(calculationRequest);

        assertThat(actual).isEqualTo(priceResponse.getPrice());

        verify(priceCalculationApiClient).calculatePrice(any(PriceCalculationRequest.class));
    }

    @Test
    void recalculatePriceWithDiscount_ShouldRecalculatePriceWithDiscount_WhenPromoCodeServiceIsAvailable() {
        PriceRecalculationDto priceRecalculationDto = PriceTestUtil.buildPriceRecalculationDto();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        when(promoCodeService.getPromoCode(priceRecalculationDto.promoCode()))
                .thenReturn(promoCodeDto);
        doNothing().when(promoCodeService).createPromoStat(priceRecalculationDto.passengerId(), promoCodeDto.value());

        Long actual = priceService.recalculatePriceWithDiscount(priceRecalculationDto);

        assertThat(actual)
                .isNotNull()
                .isLessThan(priceRecalculationDto.price());

        verify(promoCodeService).getPromoCode(priceRecalculationDto.promoCode());
        verify(promoCodeService).createPromoStat(priceRecalculationDto.passengerId(), promoCodeDto.value());
    }
}
