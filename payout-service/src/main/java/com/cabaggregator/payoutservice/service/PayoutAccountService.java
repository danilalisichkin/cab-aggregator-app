package com.cabaggregator.payoutservice.service;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountAddingDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountDto;
import com.cabaggregator.payoutservice.core.dto.page.PageDto;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.core.enums.sort.BalanceOperationSortField;
import com.cabaggregator.payoutservice.core.enums.sort.PayoutAccountSortField;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PayoutAccountService {
    PageDto<PayoutAccountDto> getPageOfPayoutAccounts(
            Integer offset, Integer limit, PayoutAccountSortField sortBy, Sort.Direction sortOrder);

    PayoutAccountDto getPayoutAccount(UUID id);

    PayoutAccountDto createPayoutAccount(PayoutAccountAddingDto addingDto);

    PayoutAccountDto updatePayoutAccount(UUID id, String stripeAccountId);

    Long getPayoutAccountBalance(UUID id);

    void setPayoutAccountActivity(UUID id, boolean active);

    PageDto<BalanceOperationDto> getPageOfBalanceOperations(
            UUID id, Integer offset, Integer limit, BalanceOperationSortField sortBy,
            Sort.Direction sortOrder, OperationType operationType, LocalDateTime startTime, LocalDateTime endTime);

    BalanceOperationDto depositToAccount(UUID id, BalanceOperationAddingDto operationAddingDto);

    BalanceOperationDto withdrawFromAccount(UUID id, BalanceOperationAddingDto operationAddingDto);
}
