package com.cabaggregator.promocodeservice.service.impl;

import com.cabaggregator.promocodeservice.core.constant.ApplicationMessages;
import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoCodeSortField;
import com.cabaggregator.promocodeservice.core.mapper.PageMapper;
import com.cabaggregator.promocodeservice.core.mapper.PromoCodeMapper;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.exception.ResourceNotFoundException;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.service.PromoCodeService;
import com.cabaggregator.promocodeservice.util.PageRequestBuilder;
import com.cabaggregator.promocodeservice.validator.PromoCodeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;

    private final PromoCodeMapper promoCodeMapper;

    private final PageMapper pageMapper;

    private final PromoCodeValidator promoCodeValidator;

    @Override
    public PageDto<PromoCodeDto> getPageOfPromoCodes(
            Integer offset, Integer limit, PromoCodeSortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        Page<PromoCode> promoCodes = promoCodeRepository.findAll(pageRequest);

        return pageMapper.pageToPageDto(
                promoCodeMapper.entityPageToDtoPage(promoCodes));
    }

    @Override
    public PromoCodeDto getPromoCode(String code) {
        return promoCodeMapper.entityToDto(
                getPromoCodeEntity(code));
    }

    @Override
    @Transactional
    public PromoCodeDto savePromoCode(PromoCodeAddingDto addingDto) {
        promoCodeValidator.validatePromoCodeUniqueness(addingDto.value());
        promoCodeValidator.validateEndDate(addingDto.endDate());

        PromoCode promoCode = promoCodeMapper.dtoToEntity(addingDto);

        return promoCodeMapper.entityToDto(
                promoCodeRepository.save(promoCode));
    }

    @Override
    @Transactional
    public PromoCodeDto updatePromoCode(String code, PromoCodeUpdatingDto updatingDto) {
        promoCodeValidator.validateEndDate(updatingDto.endDate());

        PromoCode promoCodeToUpdate = getPromoCodeEntity(code);
        promoCodeMapper.updateEntityFromDto(updatingDto, promoCodeToUpdate);

        return promoCodeMapper.entityToDto(
                promoCodeRepository.save(promoCodeToUpdate));
    }

    private PromoCode getPromoCodeEntity(String code) {
        return promoCodeRepository
                .findById(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PROMO_CODE_NOT_FOUND,
                        code));
    }
}
