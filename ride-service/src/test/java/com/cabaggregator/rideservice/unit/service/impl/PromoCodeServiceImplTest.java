package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.client.PromoCodeApiClient;
import com.cabaggregator.rideservice.client.PromoStatApiClient;
import com.cabaggregator.rideservice.client.dto.PromoCodeDto;
import com.cabaggregator.rideservice.client.dto.PromoStatAddingDto;
import com.cabaggregator.rideservice.service.impl.PromoCodeServiceImpl;
import com.cabaggregator.rideservice.util.PromoCodeTestUtil;
import com.cabaggregator.rideservice.util.RideTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PromoCodeServiceImplTest {
    @InjectMocks
    private PromoCodeServiceImpl promoCodeService;

    @Mock
    private PromoStatApiClient promoStatApiClient;

    @Mock
    private PromoCodeApiClient promoCodeApiClient;

    @Test
    void getPromoCode_ShouldReturnPromoCodeDto_WhenPromoCodeExists() {
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        when(promoCodeApiClient.getPromoCode(PromoCodeTestUtil.VALUE))
                .thenReturn(promoCodeDto);

        PromoCodeDto actual = promoCodeService.getPromoCode(PromoCodeTestUtil.VALUE);

        assertThat(actual).isEqualTo(promoCodeDto);

        verify(promoCodeApiClient).getPromoCode(PromoCodeTestUtil.VALUE);
    }

    @Test
    void createPromoStat_ShouldCreatePromoStat_WhenPromoCodeServiceAvailable() {
        UUID userId = RideTestUtil.PASSENGER_ID;
        String promoCode = PromoCodeTestUtil.VALUE;

        when(promoStatApiClient.createPromoStat(any(PromoStatAddingDto.class)))
                .thenReturn(null);

        promoCodeService.createPromoStat(userId, promoCode);

        verify(promoStatApiClient).createPromoStat(any(PromoStatAddingDto.class));
    }
}
