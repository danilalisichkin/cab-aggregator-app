package com.cabaggregator.payoutservice.service.impl;

import com.cabaggregator.payoutservice.core.constant.ApplicationMessages;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountAddingDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountDto;
import com.cabaggregator.payoutservice.core.dto.page.PageDto;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.core.enums.sort.BalanceOperationSortField;
import com.cabaggregator.payoutservice.core.enums.sort.PayoutAccountSortField;
import com.cabaggregator.payoutservice.core.mapper.PageMapper;
import com.cabaggregator.payoutservice.core.mapper.PayoutAccountMapper;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import com.cabaggregator.payoutservice.exception.ResourceNotFoundException;
import com.cabaggregator.payoutservice.repository.PayoutAccountRepository;
import com.cabaggregator.payoutservice.service.BalanceOperationService;
import com.cabaggregator.payoutservice.service.PayoutAccountService;
import com.cabaggregator.payoutservice.service.StripeService;
import com.cabaggregator.payoutservice.util.PageRequestBuilder;
import com.cabaggregator.payoutservice.validator.PayoutAccountValidator;
import com.cabaggregator.payoutservice.validator.TimeValidator;
import com.stripe.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayoutAccountServiceImpl implements PayoutAccountService {

    private final StripeService stripeService;

    private final BalanceOperationService balanceOperationService;

    private final PayoutAccountRepository payoutAccountRepository;

    private final PayoutAccountValidator payoutAccountValidator;

    private final TimeValidator timeValidator;

    private final PayoutAccountMapper payoutAccountMapper;

    private final PageMapper pageMapper;

    @Override
    public PageDto<PayoutAccountDto> getPageOfPayoutAccounts(
            Integer offset, Integer limit, PayoutAccountSortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        return pageMapper.pageToPageDto(
                payoutAccountMapper.entityPageToDtoPage(
                        payoutAccountRepository.findAll(pageRequest)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    public PayoutAccountDto getPayoutAccount(UUID id) {
        return payoutAccountMapper.entityToDto(
                getPayoutAccountEntity(id));
    }

    @Override
    @Transactional
    public PayoutAccountDto createPayoutAccount(PayoutAccountAddingDto addingDto) {
        payoutAccountValidator.validateIdUniqueness(addingDto.id());
        payoutAccountValidator.validateStripeAccountUniqueness(addingDto.stripeAccountId());

        Account stripeAccount = stripeService.retrieveAccount(addingDto.stripeAccountId());

        PayoutAccount payoutAccount = payoutAccountMapper.dtoToEntity(addingDto);
        payoutAccount.setStripeAccountId(stripeAccount.getId());
        payoutAccount.setActive(true);

        return payoutAccountMapper.entityToDto(
                payoutAccountRepository.save(payoutAccount));
    }

    @Override
    @Transactional
    @PreAuthorize("#id == authentication.principal")
    public PayoutAccountDto updatePayoutAccount(UUID id, String stripeAccountId) {
        PayoutAccount payoutAccount = getPayoutAccountEntity(id);
        Account stripeAccount = stripeService.retrieveAccount(stripeAccountId);

        payoutAccount.setStripeAccountId(stripeAccount.getId());

        return payoutAccountMapper.entityToDto(
                payoutAccountRepository.save(payoutAccount));
    }

    @Override
    @Transactional
    public void setPayoutAccountActivity(UUID id, boolean newActive) {
        PayoutAccount payoutAccount = getPayoutAccountEntity(id);

        if (payoutAccount.getActive() != newActive) {
            payoutAccount.setActive(newActive);
            payoutAccountRepository.save(payoutAccount);
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public Long getPayoutAccountBalance(UUID id) {
        PayoutAccount payoutAccount = getPayoutAccountEntity(id);

        return balanceOperationService.getAccountBalance(payoutAccount);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    public PageDto<BalanceOperationDto> getPageOfBalanceOperations(
            UUID id, Integer offset, Integer limit, BalanceOperationSortField sortBy,
            Sort.Direction sortOrder, OperationType operationType, LocalDateTime startTime, LocalDateTime endTime) {

        timeValidator.validateTime(startTime);
        timeValidator.validateTime(endTime);
        if (startTime != null && endTime != null) {
            timeValidator.validateTimeRange(startTime, endTime);
        }

        PayoutAccount payoutAccount = getPayoutAccountEntity(id);

        return balanceOperationService.getPageOfBalanceOperations(
                payoutAccount, offset, limit, sortBy, sortOrder, operationType, startTime, endTime);
    }

    @Override
    @Transactional
    public BalanceOperationDto depositToAccount(UUID id, BalanceOperationAddingDto operationAddingDto) {
        PayoutAccount payoutAccount = getPayoutAccountEntity(id);

        return balanceOperationService.processDeposit(payoutAccount, operationAddingDto);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BalanceOperationDto withdrawFromAccount(UUID id, BalanceOperationAddingDto operationAddingDto) {
        PayoutAccount payoutAccount = getPayoutAccountEntity(id);

        return balanceOperationService.processWithdraw(payoutAccount, operationAddingDto);
    }

    private PayoutAccount getPayoutAccountEntity(UUID id) {
        return payoutAccountRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PAYOUT_ACCOUNT_WITH_ID_NOT_FOUND,
                        id));
    }
}
