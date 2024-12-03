package com.cabaggregator.promocodeservice.unit.service.impl;

import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoStatSortField;
import com.cabaggregator.promocodeservice.core.mapper.PageMapper;
import com.cabaggregator.promocodeservice.core.mapper.PromoStatMapper;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import com.cabaggregator.promocodeservice.exception.BadRequestException;
import com.cabaggregator.promocodeservice.exception.ResourceNotFoundException;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.repository.PromoStatRepository;
import com.cabaggregator.promocodeservice.service.impl.PromoStatServiceImpl;
import com.cabaggregator.promocodeservice.util.PageRequestBuilder;
import com.cabaggregator.promocodeservice.util.PaginationTestUtil;
import com.cabaggregator.promocodeservice.util.PromoStatTestUtil;
import com.cabaggregator.promocodeservice.validator.PromoCodeValidator;
import com.cabaggregator.promocodeservice.validator.PromoStatValidator;
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
class PromoStatServiceImplTest {

    @InjectMocks
    private PromoStatServiceImpl promoStatService;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Mock
    private PromoStatRepository promoStatRepository;

    @Mock
    private PromoStatMapper promoStatMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private PromoCodeValidator promoCodeValidator;

    @Mock
    private PromoStatValidator promoStatValidator;

    @Test
    void getPageOfPromoStats_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        PromoStatSortField sortBy = PromoStatSortField.USER_ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().build();
        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<PromoStat> promoStatPage = PaginationTestUtil.buildPageFromSingleElement(promoStat);
        Page<PromoStatDto> promoStatDtoPage = PaginationTestUtil.buildPageFromSingleElement(promoStatDto);
        PageDto<PromoStatDto> pageDto = PaginationTestUtil.buildPageDto(promoStatDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(promoStatRepository.findAll(pageRequest))
                    .thenReturn(promoStatPage);
            when(promoStatMapper.entityPageToDtoPage(promoStatPage))
                    .thenReturn(promoStatDtoPage);
            when(pageMapper.pageToPageDto(promoStatDtoPage))
                    .thenReturn(pageDto);

            PageDto<PromoStatDto> actual = promoStatService.getPageOfPromoStats(offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(promoStatRepository).findAll(pageRequest);
            verify(promoStatMapper).entityPageToDtoPage(promoStatPage);
            verify(pageMapper).pageToPageDto(promoStatDtoPage);
        }
    }

    @Test
    void getPromoStat_ShouldReturnPromoStat_WhenPromoStatFound() {
        PromoStat promoStat =
                PromoStatTestUtil.getPromoStatBuilder()
                        .build();
        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();

        when(promoStatRepository.findById(promoStat.getId()))
                .thenReturn(Optional.of(promoStat));
        when(promoStatMapper.entityToDto(promoStat))
                .thenReturn(promoStatDto);

        PromoStatDto actual = promoStatService.getPromoStat(promoStat.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(promoStatDto);

        verify(promoStatRepository).findById(promoStat.getId());
        verify(promoStatMapper).entityToDto(promoStat);
    }

    @Test
    void getPromoStat_ShouldThrowResourceNotFoundException_WhenPromoStatNotFound() {
        PromoStat promoStat =
                PromoStatTestUtil.getPromoStatBuilder()
                        .build();

        when(promoStatRepository.findById(promoStat.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> promoStatService.getPromoStat(promoStat.getId()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(promoStatRepository).findById(promoStat.getId());
        verifyNoInteractions(promoStatMapper);
    }

    @Test
    void savePromoStat_ShouldReturnPromoStat_WhenPromoCodeIsValidAndItExists() {
        PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().build();
        PromoCode promoCode = promoStat.getPromoCode();
        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();
        PromoStatAddingDto promoStatAddingDto = PromoStatTestUtil.buildPromoStatAddingDto();

        Long initialApplicationLimit = promoCode.getLimit();

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.of(promoCode));

        doNothing().when(promoStatValidator).validatePromoCodeApplication(promoCode, promoStatAddingDto.userId());
        doNothing().when(promoCodeValidator).validatePromoCodeExpiration(promoCode.getEndDate());
        doNothing().when(promoCodeValidator).validatePromoCodeApplicationLimit(promoCode.getLimit());

        when(promoStatMapper.dtoToEntity(promoStatAddingDto))
                .thenReturn(promoStat);
        when(promoCodeRepository.save(promoCode))
                .thenReturn(promoCode);
        when(promoStatRepository.save(promoStat))
                .thenReturn(promoStat);
        when(promoStatMapper.entityToDto(promoStat))
                .thenReturn(promoStatDto);

        PromoStatDto actual = promoStatService.savePromoStat(promoStatAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(promoStatDto);

        verify(promoCodeRepository).findById(promoStatAddingDto.promoCode());
        verify(promoStatValidator).validatePromoCodeApplication(promoCode, promoStatAddingDto.userId());
        verify(promoCodeValidator).validatePromoCodeExpiration(promoCode.getEndDate());
        verify(promoCodeValidator).validatePromoCodeApplicationLimit(initialApplicationLimit);
        verify(promoStatMapper).dtoToEntity(promoStatAddingDto);
        verify(promoCodeRepository).save(promoCode);
        verify(promoStatRepository).save(promoStat);
        verify(promoStatMapper).entityToDto(promoStat);
    }

    @Test
    void savePromoStat_ShouldThrowBadRequestException_WhenPromoCodeExistsAndAlreadyHasBeenApplied() {
        PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().build();
        PromoCode promoCode = promoStat.getPromoCode();
        PromoStatAddingDto promoStatAddingDto = PromoStatTestUtil.buildPromoStatAddingDto();

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.of(promoCode));
        doThrow(new BadRequestException("error"))
                .when(promoStatValidator).validatePromoCodeApplication(promoCode, promoStatAddingDto.userId());

        assertThatThrownBy(
                () -> promoStatService.savePromoStat(promoStatAddingDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("error");

        verify(promoCodeRepository).findById(promoCode.getValue());
        verify(promoStatValidator).validatePromoCodeApplication(promoCode, promoStatAddingDto.userId());
        verifyNoInteractions(promoCodeValidator, promoStatMapper, promoStatRepository);
        verifyNoMoreInteractions(promoCodeRepository);
    }

    @Test
    void savePromoStat_ShouldThrowBadRequestException_WhenPromoCodeExistsAndHasExpired() {
        PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().build();
        PromoCode promoCode = promoStat.getPromoCode();
        PromoStatAddingDto promoStatAddingDto = PromoStatTestUtil.buildPromoStatAddingDto();

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.of(promoCode));
        doNothing().when(promoStatValidator).validatePromoCodeApplication(promoCode, promoStatAddingDto.userId());
        doThrow(new BadRequestException("error"))
                .when(promoCodeValidator).validatePromoCodeExpiration(promoCode.getEndDate());

        assertThatThrownBy(
                () -> promoStatService.savePromoStat(promoStatAddingDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("error");

        verify(promoCodeRepository).findById(promoCode.getValue());
        verify(promoStatValidator).validatePromoCodeApplication(promoCode, promoStatAddingDto.userId());
        verify(promoCodeValidator).validatePromoCodeExpiration(promoCode.getEndDate());
        verifyNoInteractions(promoStatMapper, promoStatRepository);
        verifyNoMoreInteractions(promoCodeValidator, promoCodeRepository);
    }

    @Test
    void savePromoStat_ShouldThrowBadRequestException_WhenPromoCodeExistsAndItsApplicationLimitHasReached() {
        PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().build();
        PromoCode promoCode = promoStat.getPromoCode();
        PromoStatAddingDto promoStatAddingDto = PromoStatTestUtil.buildPromoStatAddingDto();

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.of(promoCode));
        doNothing().when(promoStatValidator).validatePromoCodeApplication(promoCode, promoStatAddingDto.userId());
        doNothing().when(promoCodeValidator).validatePromoCodeExpiration(promoCode.getEndDate());
        doThrow(new BadRequestException("error"))
                .when(promoCodeValidator).validatePromoCodeApplicationLimit(promoCode.getLimit());

        assertThatThrownBy(
                () -> promoStatService.savePromoStat(promoStatAddingDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("error");

        verify(promoCodeRepository).findById(promoCode.getValue());
        verify(promoStatValidator).validatePromoCodeApplication(promoCode, promoStatAddingDto.userId());
        verify(promoCodeValidator).validatePromoCodeExpiration(promoCode.getEndDate());
        verify(promoCodeValidator).validatePromoCodeApplicationLimit(promoCode.getLimit());
        verifyNoInteractions(promoStatMapper, promoStatRepository);
        verifyNoMoreInteractions(promoCodeRepository);
    }

    @Test
    void savePromoStat_ShouldThrowResourceNotFoundException_WhenPromoCodeDoesNotExist() {
        PromoStatAddingDto promoStatAddingDto = PromoStatTestUtil.buildPromoStatAddingDto();

        when(promoCodeRepository.findById(promoStatAddingDto.promoCode()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> promoStatService.savePromoStat(promoStatAddingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(promoCodeRepository).findById(promoStatAddingDto.promoCode());
        verifyNoInteractions(promoStatValidator, promoCodeValidator, promoStatMapper, promoStatRepository);
        verifyNoMoreInteractions(promoCodeRepository);
    }
}

