package com.cabaggregator.payoutservice.core.mapper;

import com.cabaggregator.payoutservice.core.dto.PayoutAccountAddingDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountDto;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PayoutAccountMapper {
    PayoutAccount dtoToEntity(PayoutAccountAddingDto dto);

    PayoutAccountDto entityToDto(PayoutAccount entity);

    List<PayoutAccountDto> entityListToDtoList(List<PayoutAccount> enitityList);

    default Page<PayoutAccountDto> entityPageToDtoPage(Page<PayoutAccount> entityPage) {
        List<PayoutAccountDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
