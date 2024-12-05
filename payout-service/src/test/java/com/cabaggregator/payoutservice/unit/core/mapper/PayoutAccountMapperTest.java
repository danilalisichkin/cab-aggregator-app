package com.cabaggregator.payoutservice.unit.core.mapper;

import com.cabaggregator.payoutservice.core.dto.PayoutAccountAddingDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountDto;
import com.cabaggregator.payoutservice.core.mapper.PayoutAccountMapper;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
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
class PayoutAccountMapperTest {
    private final PayoutAccountMapper mapper = Mappers.getMapper(PayoutAccountMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        PayoutAccountDto payoutAccountDto = PayoutAccountTestUtil.buildPayoutAccountDto();

        PayoutAccountDto actual = mapper.entityToDto(payoutAccount);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(payoutAccountDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        PayoutAccountAddingDto payoutAccountAddingDto = PayoutAccountTestUtil.buildPayoutAccountAddingDto();

        PayoutAccount actual = mapper.dtoToEntity(payoutAccountAddingDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(payoutAccountAddingDto.id());
        assertThat(actual.getStripeAccountId()).isEqualTo(payoutAccountAddingDto.stripeAccountId());
        assertThat(actual.getActive()).isNull();
        assertThat(actual.getCreatedAt()).isNull();
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        List<PayoutAccount> entityList = Arrays.asList(payoutAccount, payoutAccount);
        PayoutAccountDto payoutAccountDto = PayoutAccountTestUtil.buildPayoutAccountDto();
        List<PayoutAccountDto> dtoList = Arrays.asList(payoutAccountDto, payoutAccountDto);

        List<PayoutAccountDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(dtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<PayoutAccount> entityList = Collections.emptyList();

        List<PayoutAccountDto> actual = mapper.entityListToDtoList(entityList);

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
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        List<PayoutAccount> entityList = Arrays.asList(payoutAccount, payoutAccount);
        Page<PayoutAccount> entityPage = PaginationTestUtil.buildPageFromList(entityList);
        PayoutAccountDto payoutAccountDto = PayoutAccountTestUtil.buildPayoutAccountDto();
        List<PayoutAccountDto> dtoList = Arrays.asList(payoutAccountDto, payoutAccountDto);
        Page<PayoutAccountDto> expectedDtoPage = PaginationTestUtil.buildPageFromList(dtoList);

        Page<PayoutAccountDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
