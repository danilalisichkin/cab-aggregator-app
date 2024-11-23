package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import com.cabaggregator.rideservice.core.enums.UserRole;
import com.cabaggregator.rideservice.core.enums.sort.PromoCodeSortField;
import com.cabaggregator.rideservice.core.mapper.PageMapper;
import com.cabaggregator.rideservice.core.mapper.PromoCodeMapper;
import com.cabaggregator.rideservice.entity.PromoCode;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.PromoCodeRepository;
import com.cabaggregator.rideservice.service.PromoCodeService;
import com.cabaggregator.rideservice.service.UserCredentialsService;
import com.cabaggregator.rideservice.util.PageRequestBuilder;
import com.cabaggregator.rideservice.validator.PromoCodeValidator;
import com.cabaggregator.rideservice.validator.UserRoleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;

    private final UserCredentialsService userCredentialsService;

    private final PromoCodeMapper promoCodeMapper;
    private final PageMapper pageMapper;

    private final PromoCodeValidator promoCodeValidator;
    private final UserRoleValidator userRoleValidator;

    @Override
    public PageDto<PromoCodeDto> getPageOfPromoCodes(
            String accessToken, Integer offset, Integer limit, PromoCodeSortField sortField, Sort.Direction sortOrder) {

        userRoleValidator.validateUserIsAdmin(getUserRole(accessToken));

        PageRequest request = PageRequestBuilder.buildPageRequest(offset, limit, sortField.getValue(), sortOrder);

        return pageMapper.pageToPageDto(
                promoCodeMapper.entityPageToDtoPage(
                        promoCodeRepository.findAll(request)));
    }

    @Override
    public PromoCodeDto getPromoCodeByValue(String codeValue) {
        return promoCodeMapper.entityToDto(
                getPromoCodeEntityByValue(codeValue));
    }

    @Override
    public PromoCodeDto getPromoCodeByValueSecured(String accessToken, String codeValue) {
        userRoleValidator.validateUserIsAdmin(getUserRole(accessToken));

        return getPromoCodeByValue(codeValue);
    }

    @Override
    @Transactional
    public PromoCodeDto savePromoCode(String accessToken, PromoCodeAddingDto promoCodeDto) {
        userRoleValidator.validateUserIsAdmin(getUserRole(accessToken));

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
        userRoleValidator.validateUserIsAdmin(getUserRole(accessToken));

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

    private UserRole getUserRole(String accessToken) {
        return userCredentialsService.getUserRole(accessToken)
                .orElseThrow(() -> new BadRequestException(
                        ApplicationMessages.NO_USER_ROLE_PROVIDED));
    }
}
