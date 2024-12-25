package com.cabaggregator.payoutservice.entity.specification;

import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.BalanceOperation_;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BalanceOperationSpecification {

    public static Specification<BalanceOperation> hasPayoutAccount(PayoutAccount payoutAccount) {
        return (root, query, criteriaBuilder) -> payoutAccount != null
                ? criteriaBuilder.equal(root.get(BalanceOperation_.PAYOUT_ACCOUNT), payoutAccount)
                : criteriaBuilder.conjunction();
    }

    public static Specification<BalanceOperation> hasCreatedAtAfter(LocalDateTime start) {
        return (root, query, criteriaBuilder) -> start != null
                ? criteriaBuilder.greaterThanOrEqualTo(root.get(BalanceOperation_.CREATED_AT), start)
                : criteriaBuilder.conjunction();
    }

    public static Specification<BalanceOperation> hasCreatedAtBefore(LocalDateTime end) {
        return (root, query, criteriaBuilder) -> end != null
                ? criteriaBuilder.lessThanOrEqualTo(root.get(BalanceOperation_.CREATED_AT), end)
                : criteriaBuilder.conjunction();
    }

    public static Specification<BalanceOperation> hasOperationType(OperationType type) {
        return (root, query, criteriaBuilder) -> type != null
                ? criteriaBuilder.equal(root.get(BalanceOperation_.TYPE), type)
                : criteriaBuilder.conjunction();
    }
}
