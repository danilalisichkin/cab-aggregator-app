package com.cabaggregator.payoutservice.entity;

import com.cabaggregator.payoutservice.core.enums.OperationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@Table(name = "balance_operations", indexes = {
        @Index(name = "idx_payout_account_type_created_at", columnList = "payout_account_user_id, type")})
public class BalanceOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payout_account_id", nullable = false)
    private PayoutAccount payoutAccount;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @Column(nullable = false)
    private String transcript;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceOperation that = (BalanceOperation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(payoutAccount, that.payoutAccount) &&
                Objects.equals(amount, that.amount) &&
                type == that.type &&
                Objects.equals(transcript, that.transcript) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payoutAccount, amount, type, transcript, createdAt);
    }
}
