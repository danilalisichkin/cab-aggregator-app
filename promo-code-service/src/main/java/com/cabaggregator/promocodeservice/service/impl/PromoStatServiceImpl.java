package com.cabaggregator.promocodeservice.service.impl;

import com.cabaggregator.promocodeservice.core.constant.ApplicationMessages;
import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoStatSortField;
import com.cabaggregator.promocodeservice.core.mapper.PageMapper;
import com.cabaggregator.promocodeservice.core.mapper.PromoStatMapper;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import com.cabaggregator.promocodeservice.exception.ResourceNotFoundException;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.repository.PromoStatRepository;
import com.cabaggregator.promocodeservice.service.PromoStatService;
import com.cabaggregator.promocodeservice.util.PageRequestBuilder;
import com.cabaggregator.promocodeservice.validator.PromoCodeValidator;
import com.cabaggregator.promocodeservice.validator.PromoStatValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromoStatServiceImpl implements PromoStatService {

    private final PromoCodeRepository promoCodeRepository;

    private final PromoStatRepository promoStatRepository;

    private final PromoStatMapper promoStatMapper;

    private final PageMapper pageMapper;

    private final PromoCodeValidator promoCodeValidator;

    private final PromoStatValidator promoStatValidator;

    @Override
    public PageDto<PromoStatDto> getPageOfPromoStats(
            Integer offset, Integer limit, PromoStatSortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        Page<PromoStat> promoStats = promoStatRepository.findAll(pageRequest);

        return pageMapper.pageToPageDto(
                promoStatMapper.entityPageToDtoPage(promoStats));
    }

    @Override
    public PromoStatDto getPromoStat(Long id) {
        return promoStatMapper.entityToDto(
                getPromoStatEntity(id));
    }

    @Override
    @Transactional
    public PromoStatDto savePromoStat(PromoStatAddingDto addingDto) {
        PromoCode promoCode = getPromoCodeEntity(addingDto.promoCode());

        promoStatValidator.validatePromoCodeApplication(promoCode, addingDto.userId());
        promoCodeValidator.validatePromoCodeExpiration(promoCode.getEndDate());
        promoCodeValidator.validatePromoCodeApplicationLimit(promoCode.getLimit());

        PromoStat promoStat = promoStatMapper.dtoToEntity(addingDto);
        promoStat.setPromoCode(promoCode);

        promoCode.setLimit(promoCode.getLimit() - 1);
        promoCodeRepository.save(promoCode);

        return promoStatMapper.entityToDto(
                promoStatRepository.save(promoStat));
    }

    private PromoStat getPromoStatEntity(Long id) {
        return promoStatRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PROMO_STAT_WITH_ID_NOT_FOUND,
                        id));
    }

    private PromoCode getPromoCodeEntity(String code) {
        return promoCodeRepository
                .findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PROMO_CODE_NOT_FOUND,
                        code));
    }
}
