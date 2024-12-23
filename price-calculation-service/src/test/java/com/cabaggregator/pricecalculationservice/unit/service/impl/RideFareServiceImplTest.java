package com.cabaggregator.pricecalculationservice.unit.service.impl;

import com.cabaggregator.pricecalculationservice.entity.RideFare;
import com.cabaggregator.pricecalculationservice.exception.InternalErrorException;
import com.cabaggregator.pricecalculationservice.repository.RideFareRepository;
import com.cabaggregator.pricecalculationservice.service.impl.RideFareServiceImpl;
import com.cabaggregator.pricecalculationservice.util.RideFareTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RideFareServiceImplTest {
    @InjectMocks
    private RideFareServiceImpl rideFareService;

    @Mock
    private RideFareRepository rideFareRepository;

    @Test
    void getRideFare_ShouldReturnRideFare_WhenRideFareFound() {
        RideFare rideFare = RideFareTestUtil.getRideFareBuilder().build();

        when(rideFareRepository.findById(rideFare.getName()))
                .thenReturn(Optional.of(rideFare));

        RideFare actual = rideFareService.getRideFare(rideFare.getName());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(rideFare);

        verify(rideFareRepository).findById(rideFare.getName());
    }

    @Test
    void getRideFare_ShouldThrowInternalErrorException_WhenRideFareNotFound() {
        RideFare rideFare = RideFareTestUtil.getRideFareBuilder().build();

        when(rideFareRepository.findById(rideFare.getName()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> rideFareService.getRideFare(rideFare.getName()))
                .isInstanceOf(InternalErrorException.class);

        verify(rideFareRepository).findById(rideFare.getName());
    }
}
