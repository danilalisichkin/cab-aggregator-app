package com.cabaggregator.promocodeservice.service;

import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoCodeSortField;
import org.springframework.data.domain.Sort;

public interface PromoCodeService {
    PageDto<PromoCodeDto> getPageOfPromoCodes(
            Integer offset, Integer limit, PromoCodeSortField sortBy, Sort.Direction sortOrder);

    PromoCodeDto getPromoCode(String code);

    PromoCodeDto savePromoCode(PromoCodeAddingDto addingDto);

    PromoCodeDto updatePromoCode(String code, PromoCodeUpdatingDto updatingDto);
}
