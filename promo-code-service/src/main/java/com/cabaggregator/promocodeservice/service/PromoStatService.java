package com.cabaggregator.promocodeservice.service;

import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoStatSortField;
import org.springframework.data.domain.Sort;

public interface PromoStatService {
    PageDto<PromoStatDto> getPageOfPromoStats(
            Integer offset, Integer limit, PromoStatSortField sortBy, Sort.Direction sortOrder);

    PromoStatDto getPromoStat(Long id);

    PromoStatDto savePromoStat(PromoStatAddingDto addingDto);
}
