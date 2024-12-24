package com.cabaggregator.payoutservice.service;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.dto.page.PageDto;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.core.enums.sort.BalanceOperationSortField;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public interface BalanceOperationService {
    PageDto<BalanceOperationDto> getPageOfBalanceOperations(
            PayoutAccount payoutAccount, Integer offset, Integer limit, BalanceOperationSortField sortBy,
            Sort.Direction sortOrder, OperationType operationType, LocalDateTime startTime, LocalDateTime endTime);

    BalanceOperationDto processDeposit(PayoutAccount payoutAccount, BalanceOperationAddingDto operationAddingDto);

    BalanceOperationDto processWithdraw(PayoutAccount payoutAccount, BalanceOperationAddingDto operationAddingDto);

    Long getAccountBalance(PayoutAccount payoutAccount);
}
