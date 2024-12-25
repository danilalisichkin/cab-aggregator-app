package com.cabaggregator.paymentservice.core.mapper;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentDto;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.entity.Payment;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    @Mapping(source = "id", target = "paymentIntentId")
    PaymentResponse intentToResponse(PaymentIntent intent);

    @Mapping(source = "paymentAccount.id", target = "paymentAccountId")
    @Mapping(source = "context.type", target = "contextType")
    @Mapping(source = "context.contextId", target = "contextId")
    PaymentDto entityToDto(Payment entity);

    List<PaymentDto> entityListToDtoList(List<Payment> enitityList);

    @Mapping(source = "id", target = "paymentMethodId")
    @Mapping(source = "card.brand", target = "brand")
    @Mapping(source = "card.last4", target = "last4Digits")
    PaymentCardDto methodToCardDto(PaymentMethod method);

    List<PaymentCardDto> methodListToCardDtoList(List<PaymentMethod> paymentMethods);
}
