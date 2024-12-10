package com.cabaggregator.payoutservice.core.mapper;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.entity.BalanceOperation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BalanceOperationMapper {
    @Mapping(source = "payoutAccount.id", target = "payoutAccountId")
    BalanceOperationDto entityToDto(BalanceOperation entity);

    BalanceOperation dtoToEntity(BalanceOperationAddingDto dto);

    List<BalanceOperationDto> entityListToDtoList(List<BalanceOperation> enitityList);

    default Page<BalanceOperationDto> entityPageToDtoPage(Page<BalanceOperation> entityPage) {
        List<BalanceOperationDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
