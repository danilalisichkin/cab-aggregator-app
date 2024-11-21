package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.page.PagedDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import com.cabaggregator.rideservice.core.enums.sort.PromoCodeSort;

public interface PromoCodeService {
    PagedDto<PromoCodeDto> getPageOfPromoCodes(String accessToken, Integer offset, Integer limit, PromoCodeSort sort);

    PromoCodeDto getPromoCodeByValue(String codeValue);

    PromoCodeDto getPromoCodeByValueSecured(String accessToken, String codeValue);

    PromoCodeDto savePromoCode(String accessToken, PromoCodeAddingDto promoCodeDto);

    PromoCodeDto updatePromoCode(String accessToken, String codeValue, PromoCodeUpdatingDto promoCodeDto);
}
