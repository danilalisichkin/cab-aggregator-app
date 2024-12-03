package com.cabaggregator.promocodeservice.unit.service.impl;

import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoCodeSortField;
import com.cabaggregator.promocodeservice.core.mapper.PageMapper;
import com.cabaggregator.promocodeservice.core.mapper.PromoCodeMapper;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.exception.BadRequestException;
import com.cabaggregator.promocodeservice.exception.DataUniquenessConflictException;
import com.cabaggregator.promocodeservice.exception.ResourceNotFoundException;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.service.impl.PromoCodeServiceImpl;
import com.cabaggregator.promocodeservice.util.PageRequestBuilder;
import com.cabaggregator.promocodeservice.util.PaginationTestUtil;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import com.cabaggregator.promocodeservice.validator.PromoCodeValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PromoCodeServiceImplTest {

    @InjectMocks
    private PromoCodeServiceImpl promoCodeService;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Mock
    private PromoCodeMapper promoCodeMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private PromoCodeValidator promoCodeValidator;

    @Test
    void getPageOfPromoCodes_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        PromoCodeSortField sortBy = PromoCodeSortField.LIMIT;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<PromoCode> promoCodePage = PaginationTestUtil.buildPageFromSingleElement(promoCode);
        Page<PromoCodeDto> promoCodeDtoPage = PaginationTestUtil.buildPageFromSingleElement(promoCodeDto);
        PageDto<PromoCodeDto> pageDto = PaginationTestUtil.buildPageDto(promoCodeDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(promoCodeRepository.findAll(pageRequest))
                    .thenReturn(promoCodePage);
            when(promoCodeMapper.entityPageToDtoPage(promoCodePage))
                    .thenReturn(promoCodeDtoPage);
            when(pageMapper.pageToPageDto(promoCodeDtoPage))
                    .thenReturn(pageDto);

            PageDto<PromoCodeDto> actual = promoCodeService.getPageOfPromoCodes(offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(promoCodeRepository).findAll(pageRequest);
            verify(promoCodeMapper).entityPageToDtoPage(promoCodePage);
            verify(pageMapper).pageToPageDto(promoCodeDtoPage);
        }
    }

    @Test
    void getPromoCode_ShouldReturnPromoCode_WhenPromoCodeFound() {
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.of(promoCode));
        when(promoCodeMapper.entityToDto(promoCode))
                .thenReturn(promoCodeDto);

        PromoCodeDto actual = promoCodeService.getPromoCode(promoCode.getValue());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(promoCodeDto);

        verify(promoCodeRepository).findById(promoCode.getValue());
        verify(promoCodeMapper).entityToDto(promoCode);
    }

    @Test
    void getPromoCode_ShouldThrowResourceNotFoundException_WhenPromoCodeNotFound() {
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> promoCodeService.getPromoCode(promoCode.getValue()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(promoCodeRepository).findById(promoCode.getValue());
        verifyNoInteractions(promoCodeMapper);
    }

    @Test
    void savePromoCode_ShouldThrowDataUniquenessException_WhenPromoCodeNotUnique() {
        PromoCodeAddingDto promoCodeAddingDto = PromoCodeTestUtil.buildPromoCodeAddingDto();

        doThrow(new DataUniquenessConflictException("error"))
                .when(promoCodeValidator).validatePromoCodeUniqueness(promoCodeAddingDto.value());

        assertThatThrownBy(
                () -> promoCodeService.savePromoCode(promoCodeAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class)
                .hasMessage("error");

        verify(promoCodeValidator).validatePromoCodeUniqueness(promoCodeAddingDto.value());
        verifyNoInteractions(promoCodeRepository, promoCodeMapper, pageMapper);
    }

    @Test
    void savePromoCode_ShouldThrowBadRequestException_WhenPromoCodeEndDateIsInvalid() {
        PromoCodeAddingDto promoCodeAddingDto = PromoCodeTestUtil.buildPromoCodeAddingDto();

        doNothing().when(promoCodeValidator).validatePromoCodeUniqueness(promoCodeAddingDto.value());
        doThrow(new BadRequestException("error"))
                .when(promoCodeValidator).validateEndDate(promoCodeAddingDto.endDate());

        assertThatThrownBy(
                () -> promoCodeService.savePromoCode(promoCodeAddingDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("error");

        verify(promoCodeValidator).validatePromoCodeUniqueness(promoCodeAddingDto.value());
        verify(promoCodeValidator).validateEndDate(promoCodeAddingDto.endDate());
        verifyNoInteractions(promoCodeRepository, promoCodeMapper, pageMapper);
    }

    @Test
    void savePromoCode_ShouldReturnPromoCode_WhenPromoCodeIsValid() {
        PromoCodeAddingDto promoCodeAddingDto = PromoCodeTestUtil.buildPromoCodeAddingDto();
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        doNothing().when(promoCodeValidator).validatePromoCodeUniqueness(promoCodeAddingDto.value());
        doNothing().when(promoCodeValidator).validateEndDate(promoCodeAddingDto.endDate());
        when(promoCodeMapper.dtoToEntity(promoCodeAddingDto))
                .thenReturn(promoCode);
        when(promoCodeRepository.save(promoCode))
                .thenReturn(promoCode);
        when(promoCodeMapper.entityToDto(promoCode))
                .thenReturn(promoCodeDto);

        PromoCodeDto actual = promoCodeService.savePromoCode(promoCodeAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(promoCodeDto);

        verify(promoCodeValidator).validatePromoCodeUniqueness(promoCodeAddingDto.value());
        verify(promoCodeValidator).validateEndDate(promoCodeAddingDto.endDate());
        verify(promoCodeMapper).dtoToEntity(promoCodeAddingDto);
        verify(promoCodeRepository).save(promoCode);
        verify(promoCodeMapper).entityToDto(promoCode);
    }

    @Test
    void updatePromoCode_ShouldReturnPromoCode_WhenPromoCodeFound() {
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();
        PromoCodeUpdatingDto promoCodeUpdatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDto();

        doNothing().when(promoCodeValidator).validateEndDate(promoCodeUpdatingDto.endDate());
        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.of(promoCode));
        when(promoCodeRepository.save(promoCode))
                .thenReturn(promoCode);
        when(promoCodeMapper.entityToDto(promoCode))
                .thenReturn(promoCodeDto);

        PromoCodeDto actual = promoCodeService.updatePromoCode(promoCode.getValue(), promoCodeUpdatingDto);

        assertThat(actual).isNotNull().isEqualTo(promoCodeDto);

        verify(promoCodeValidator).validateEndDate(promoCodeUpdatingDto.endDate());
        verify(promoCodeRepository).findById(promoCode.getValue());
        verify(promoCodeMapper).updateEntityFromDto(promoCodeUpdatingDto, promoCode);
        verify(promoCodeRepository).save(promoCode);
        verify(promoCodeMapper).entityToDto(promoCode);
    }

    @Test
    void updatePromoCode_ShouldThrowResourceNotFoundException_WhenPromoCodeNotFound() {
        PromoCodeUpdatingDto promoCodeUpdatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDto();

        doNothing().when(promoCodeValidator).validateEndDate(promoCodeUpdatingDto.endDate());
        when(promoCodeRepository.findById(PromoCodeTestUtil.NOT_EXISTING_CODE))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> promoCodeService.updatePromoCode(PromoCodeTestUtil.NOT_EXISTING_CODE, promoCodeUpdatingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(promoCodeValidator).validateEndDate(promoCodeUpdatingDto.endDate());
        verify(promoCodeRepository).findById(PromoCodeTestUtil.NOT_EXISTING_CODE);
        verifyNoInteractions(promoCodeMapper);
        verifyNoMoreInteractions(promoCodeRepository);
    }
}