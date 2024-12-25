package com.cabaggregator.payoutservice.unit.core.mapper;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.mapper.BalanceOperationMapper;
import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import com.cabaggregator.payoutservice.util.BalanceOperationTestUtil;
import com.cabaggregator.payoutservice.util.PaginationTestUtil;
import com.cabaggregator.payoutservice.util.PayoutAccountTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class BalanceOperationMapperTest {
    private final BalanceOperationMapper mapper = Mappers.getMapper(BalanceOperationMapper.class);

    private BalanceOperation buildBalanceOperation() {
        PayoutAccount account = PayoutAccountTestUtil.getPayoutAccountBuilder().build();

        return BalanceOperationTestUtil
                .getBalanceOperationBuilder()
                .payoutAccount(account)
                .build();
    }

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        BalanceOperation balanceOperation = buildBalanceOperation();
        BalanceOperationDto balanceOperationDto = BalanceOperationTestUtil.buildBalanceOperationDto();

        BalanceOperationDto actual = mapper.entityToDto(balanceOperation);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(balanceOperationDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void dtoToEntity_ShouldConvertEntityToDto_WhenDtoIsNotNull() {
        BalanceOperation balanceOperation = buildBalanceOperation();
        BalanceOperationAddingDto balanceOperationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(balanceOperation.getAmount());

        BalanceOperation actual = mapper.dtoToEntity(balanceOperationAddingDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getPayoutAccount()).isNull();
        assertThat(actual.getAmount()).isEqualTo(balanceOperation.getAmount());
        assertThat(actual.getType()).isNull();
        assertThat(actual.getTranscript()).isEqualTo(balanceOperation.getTranscript());
        assertThat(actual.getCreatedAt()).isNull();
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        BalanceOperation balanceOperation = buildBalanceOperation();
        List<BalanceOperation> entityList = Arrays.asList(balanceOperation, balanceOperation);
        BalanceOperationDto balanceOperationDto = BalanceOperationTestUtil.buildBalanceOperationDto();
        List<BalanceOperationDto> dtoList = Arrays.asList(balanceOperationDto, balanceOperationDto);

        List<BalanceOperationDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(dtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<BalanceOperation> entityList = Collections.emptyList();

        List<BalanceOperationDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenEntityListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
        BalanceOperation balanceOperation = buildBalanceOperation();
        List<BalanceOperation> entityList = Arrays.asList(balanceOperation, balanceOperation);
        Page<BalanceOperation> entityPage = PaginationTestUtil.buildPageFromList(entityList);
        BalanceOperationDto balanceOperationDto = BalanceOperationTestUtil.buildBalanceOperationDto();
        List<BalanceOperationDto> dtoList = Arrays.asList(balanceOperationDto, balanceOperationDto);
        Page<BalanceOperationDto> expectedDtoPage = PaginationTestUtil.buildPageFromList(dtoList);

        Page<BalanceOperationDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
