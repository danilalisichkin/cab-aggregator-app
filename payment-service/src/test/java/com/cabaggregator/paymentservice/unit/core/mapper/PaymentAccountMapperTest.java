package com.cabaggregator.paymentservice.unit.core.mapper;

import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
import com.cabaggregator.paymentservice.core.mapper.PaymentAccountMapper;
import com.cabaggregator.paymentservice.entity.PaymentAccount;
import com.cabaggregator.paymentservice.util.PaginationTestUtil;
import com.cabaggregator.paymentservice.util.PaymentAccountTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class PaymentAccountMapperTest {
    private final PaymentAccountMapper mapper = Mappers.getMapper(PaymentAccountMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        PaymentAccountDto paymentAccountDto = PaymentAccountTestUtil.buildPaymentAccountDto();

        PaymentAccountDto actual = mapper.entityToDto(paymentAccount);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentAccountDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        PaymentAccountAddingDto paymentAccountAddingDto = PaymentAccountTestUtil.buildPaymentAccountAddingDto();

        PaymentAccount actual = mapper.dtoToEntity(paymentAccountAddingDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(paymentAccountAddingDto.userId());
        assertThat(actual.getStripeCustomerId()).isNull();
        assertThat(actual.getCreatedAt()).isNull();
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        PaymentAccountDto paymentAccountDto = PaymentAccountTestUtil.buildPaymentAccountDto();
        List<PaymentAccount> entityList = Arrays.asList(paymentAccount, paymentAccount);
        List<PaymentAccountDto> expectedDtoList = Arrays.asList(paymentAccountDto, paymentAccountDto);

        List<PaymentAccountDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<PaymentAccount> entityList = Collections.emptyList();

        List<PaymentAccountDto> actual = mapper.entityListToDtoList(entityList);

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
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        List<PaymentAccount> entityList = Arrays.asList(paymentAccount, paymentAccount);
        Page<PaymentAccount> entityPage = PaginationTestUtil.buildPageFromList(entityList);
        PaymentAccountDto paymentAccountDto = PaymentAccountTestUtil.buildPaymentAccountDto();
        List<PaymentAccountDto> dtoList = Arrays.asList(paymentAccountDto, paymentAccountDto);
        Page<PaymentAccountDto> expectedDtoPage = PaginationTestUtil.buildPageFromList(dtoList);

        Page<PaymentAccountDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}

