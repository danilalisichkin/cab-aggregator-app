package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.page.PagedDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import com.cabaggregator.rideservice.core.enums.sort.PromoCodeSort;
import com.cabaggregator.rideservice.core.mapper.PageMapper;
import com.cabaggregator.rideservice.core.mapper.PromoCodeMapper;
import com.cabaggregator.rideservice.entity.PromoCode;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.PromoCodeRepository;
import com.cabaggregator.rideservice.service.PromoCodeService;
import com.cabaggregator.rideservice.util.PageRequestBuilder;
import com.cabaggregator.rideservice.validator.PromoCodeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    public PagedDto<PromoCodeDto> getPageOfPromoCodes(String accessToken, Integer offset, Integer limit, PromoCodeSort sort) {
        PageRequest request = PageRequestBuilder.buildPageRequest(offset, limit, sort.getSortValue());

        return pageMapper.pageToPagedDto(
                promoCodeMapper.entityPageToDtoPage(
                        promoCodeRepository.findAll(request)));
    }

    @Override
    public PromoCodeDto getPromoCodeByValue(String codeValue) {
        return promoCodeMapper.entityToDto(
                getPromoCodeEntityByValue(codeValue));
    }

    @Override
    @Transactional
    public PromoCodeDto savePromoCode(String accessToken, PromoCodeAddingDto promoCodeDto) {
        promoCodeValidator.validatePromoCodeUniqueness(promoCodeDto.value());
        promoCodeValidator.validateStartDate(promoCodeDto.startDate());
        promoCodeValidator.validateEndDate(promoCodeDto.endDate());
        promoCodeValidator.validateStartDateWithEndDate(promoCodeDto.startDate(), promoCodeDto.endDate());

        return promoCodeMapper.entityToDto(
                promoCodeRepository.save(
                        promoCodeMapper.dtoToEntity(promoCodeDto)));
    }

    @Override
    @Transactional
    public PromoCodeDto updatePromoCode(String accessToken, String codeValue, PromoCodeUpdatingDto promoCodeDto) {
        PromoCode promoCodeToUpdate = getPromoCodeEntityByValue(codeValue);
        promoCodeValidator.validateStartDateWithEndDate(promoCodeDto.startDate(), promoCodeDto.endDate());

        promoCodeMapper.updateEntityFromDto(promoCodeDto, promoCodeToUpdate);

        return promoCodeMapper.entityToDto(
                promoCodeRepository.save(promoCodeToUpdate));
    }

    private PromoCode getPromoCodeEntityByValue(String value) {
        return promoCodeRepository
                .findByValue(value)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PROMO_CODE_NOT_FOUND,
                        value));
    }
}
