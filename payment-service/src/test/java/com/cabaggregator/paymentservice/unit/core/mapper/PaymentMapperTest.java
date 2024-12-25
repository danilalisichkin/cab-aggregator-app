package com.cabaggregator.paymentservice.unit.core.mapper;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentDto;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.mapper.PaymentMapper;
import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.util.PaymentContextTestUtil;
import com.cabaggregator.paymentservice.util.PaymentTestUtil;
import com.cabaggregator.paymentservice.util.StripeTestUtil;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class PaymentMapperTest {
    private final PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        Payment payment = PaymentTestUtil.getPaymentBuilder().build();
        payment.setContext(PaymentContextTestUtil.getPaymentContextBuilder().build());
        PaymentDto paymentDto = PaymentTestUtil.buildPaymentDto();

        PaymentDto actual = mapper.entityToDto(payment);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        Payment payment = PaymentTestUtil.getPaymentBuilder().build();
        payment.setContext(PaymentContextTestUtil.getPaymentContextBuilder().build());
        PaymentDto paymentDto = PaymentTestUtil.buildPaymentDto();
        List<Payment> entityList = Arrays.asList(payment, payment);
        List<PaymentDto> expectedDtoList = Arrays.asList(paymentDto, paymentDto);

        List<PaymentDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Payment> entityList = Collections.emptyList();

        List<PaymentDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void intentToResponse_shouldConvertIntentToResponse_WhenIntentIsNotNull() {
        PaymentIntent paymentIntent = StripeTestUtil.buildPaymentIntent();
        PaymentResponse paymentResponse = mapper.intentToResponse(paymentIntent);

        PaymentResponse actual = mapper.intentToResponse(paymentIntent);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentResponse);
    }

    @Test
    void intentToResponse_ShouldReturnNull_WhenIntentIsNull() {
        assertThat(mapper.intentToResponse(null)).isNull();
    }

    @Test
    void methodToCardDto_ShouldConvertMethodToCardDto_WhenMethodIsNotNull() {
        PaymentMethod paymentMethod = StripeTestUtil.buildPaymentMethod();
        PaymentCardDto paymentCardDto = PaymentTestUtil.buildPaymentCardDto();

        PaymentCardDto actual = mapper.methodToCardDto(paymentMethod);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentCardDto);
    }


    @Test
    void methodToCardDto_ShouldReturnNull_WhenMethodIsNull() {
        assertThat(mapper.methodToCardDto(null)).isNull();
    }

    @Test
    void methodListToCardDtoList_ShouldConvertMethodListToCardDtoList_WhenListIsNotEmpty() {
        PaymentMethod paymentMethod = StripeTestUtil.buildPaymentMethod();
        List<PaymentMethod> paymentMethodList = List.of(paymentMethod, paymentMethod);
        PaymentCardDto paymentCardDto = PaymentTestUtil.buildPaymentCardDto();
        List<PaymentCardDto> paymentCardDtoList = List.of(paymentCardDto, paymentCardDto);

        List<PaymentCardDto> actual = mapper.methodListToCardDtoList(paymentMethodList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentCardDtoList);
    }

    @Test
    void methodListToCardDtoList_ShouldReturnEmptyList_WhenListIsEmpty() {
        List<PaymentMethod> paymentMethodList = Collections.emptyList();

        List<PaymentCardDto> actual = mapper.methodListToCardDtoList(paymentMethodList);

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }
}
