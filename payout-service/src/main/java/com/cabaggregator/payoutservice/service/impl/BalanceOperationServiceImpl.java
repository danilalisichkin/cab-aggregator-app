package com.cabaggregator.payoutservice.service.impl;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.dto.page.PageDto;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.core.enums.sort.BalanceOperationSortField;
import com.cabaggregator.payoutservice.core.mapper.BalanceOperationMapper;
import com.cabaggregator.payoutservice.core.mapper.PageMapper;
import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import com.cabaggregator.payoutservice.entity.specification.BalanceOperationSpecification;
import com.cabaggregator.payoutservice.repository.BalanceOperationRepository;
import com.cabaggregator.payoutservice.service.BalanceOperationService;
import com.cabaggregator.payoutservice.service.StripeService;
import com.cabaggregator.payoutservice.util.PageRequestBuilder;
import com.cabaggregator.payoutservice.validator.BalanceOperationValidator;
import com.stripe.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BalanceOperationServiceImpl implements BalanceOperationService {

    private final StripeService stripeService;

    private final BalanceOperationRepository balanceOperationRepository;

    private final BalanceOperationValidator balanceOperationValidator;

    private final BalanceOperationMapper balanceOperationMapper;

    private final PageMapper pageMapper;

    @Override
    public PageDto<BalanceOperationDto> getPageOfBalanceOperations(
            PayoutAccount payoutAccount, Integer offset, Integer limit, BalanceOperationSortField sortBy,
            Sort.Direction sortOrder, OperationType operationType, LocalDateTime startTime, LocalDateTime endTime) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        Specification<BalanceOperation> spec = Specification
                .where(BalanceOperationSpecification.hasPayoutAccount(payoutAccount))
                .and(BalanceOperationSpecification.hasCreatedAtBefore(startTime))
                .and(BalanceOperationSpecification.hasCreatedAtAfter(endTime))
                .and(BalanceOperationSpecification.hasOperationType(operationType));

        Page<BalanceOperation> balanceOperations =
                balanceOperationRepository.findAll(spec, pageRequest);

        return pageMapper.pageToPageDto(
                balanceOperationMapper.entityPageToDtoPage(balanceOperations));
    }

    @Override
    @Transactional
    public BalanceOperationDto processDeposit(
            PayoutAccount payoutAccount, BalanceOperationAddingDto operationAddingDto) {

        balanceOperationValidator.validateDepositAmount(operationAddingDto.amount());

        BalanceOperation savedOperation =
                saveBalanceOperation(payoutAccount, operationAddingDto, OperationType.DEPOSIT);

        return balanceOperationMapper.entityToDto(savedOperation);
    }

    @Override
    @Transactional
    public BalanceOperationDto processWithdraw(
            PayoutAccount payoutAccount, BalanceOperationAddingDto operationAddingDto) {

        Long currentBalance = balanceOperationRepository.getBalance(payoutAccount);
        balanceOperationValidator.validateWithdrawAmount(currentBalance, operationAddingDto.amount());

        Account stripeAccount = stripeService.retrieveAccount(payoutAccount.getStripeAccountId());
        stripeService.createPayout(stripeAccount, operationAddingDto.amount());

        BalanceOperation savedOperation =
                saveBalanceOperation(payoutAccount, operationAddingDto, OperationType.WITHDRAWAL);

        return balanceOperationMapper.entityToDto(savedOperation);
    }

    private BalanceOperation saveBalanceOperation(
            PayoutAccount payoutAccount, BalanceOperationAddingDto operationAddingDto, OperationType operationType) {

        BalanceOperation newBalanceOperation =
                BalanceOperation.builder()
                        .payoutAccount(payoutAccount)
                        .amount(operationAddingDto.amount())
                        .transcript(operationAddingDto.transcript())
                        .type(operationType)
                        .build();

        return balanceOperationRepository.save(newBalanceOperation);
    }
}
