package com.cabaggregator.paymentservice.unit.core.mapper;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.mapper.PaymentMapper;
import com.cabaggregator.paymentservice.util.PaymentTestUtil;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PaymentMapperTest {
    private final PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void intentToResponse_shouldConvertIntentToResponse_WhenIntentIsNotNull() {
        PaymentIntent paymentIntent = PaymentTestUtil.buildPaymentIntent();
        PaymentResponse paymentResponse = mapper.intentToResponse(paymentIntent);

        PaymentResponse actual = mapper.intentToResponse(paymentIntent);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentResponse);
    }

    @Test
    void intentToResponse_shouldReturnNull_WhenIntentIsNull() {
        assertThat(mapper.intentToResponse(null)).isNull();
    }

    @Test
    void methodToCardDto_shouldConvertMethodToCardDto_WhenMethodIsNotNull() {
        PaymentMethod paymentMethod = PaymentTestUtil.buildPaymentMethod();
        PaymentCardDto paymentCardDto = PaymentTestUtil.buildPaymentCardDto();

        PaymentCardDto actual = mapper.methodToCardDto(paymentMethod);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentCardDto);
    }


    @Test
    void methodToCardDto_shouldReturnNull_WhenMethodIsNull() {
        assertThat(mapper.methodToCardDto(null)).isNull();
    }

    @Test
    void methodListToCardDtoList_shouldConvertMethodListToCardDtoList_WhenListIsNotEmpty() {
        PaymentMethod paymentMethod = PaymentTestUtil.buildPaymentMethod();
        List<PaymentMethod> paymentMethodList = List.of(paymentMethod, paymentMethod);

        PaymentCardDto paymentCardDto = PaymentTestUtil.buildPaymentCardDto();
        List<PaymentCardDto> paymentCardDtoList = List.of(paymentCardDto, paymentCardDto);

        List<PaymentCardDto> actual = mapper.methodListToCardDtoList(paymentMethodList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentCardDtoList);
    }

    @Test
    void methodListToCardDtoList_shouldReturnEmptyList_WhenListIsEmpty() {
        List<PaymentMethod> paymentMethodList = Collections.emptyList();

        List<PaymentCardDto> actual = mapper.methodListToCardDtoList(paymentMethodList);

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }
}
