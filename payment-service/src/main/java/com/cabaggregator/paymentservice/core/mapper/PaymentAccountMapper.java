package com.cabaggregator.paymentservice.core.mapper;

import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
import com.cabaggregator.paymentservice.entity.PaymentAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentAccountMapper {

    PaymentAccountDto entityToDto(PaymentAccount entity);

    @Mapping(source = "userId", target = "id")
    PaymentAccount dtoToEntity(PaymentAccountAddingDto dto);

    List<PaymentAccountDto> entityListToDtoList(List<PaymentAccount> enitityList);

    default Page<PaymentAccountDto> entityPageToDtoPage(Page<PaymentAccount> entityPage) {
        List<PaymentAccountDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
