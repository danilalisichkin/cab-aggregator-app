package com.cabaggregator.pricecalculationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "demand_coefficients")
public class DemandCoefficient {
    @Id
    private String demand;

    @Column(nullable = false)
    private Integer minOrders;

    @Column(nullable = false)
    private Double priceCoefficient;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DemandCoefficient that = (DemandCoefficient) obj;

        if (!demand.equals(that.demand)) return false;
        if (!minOrders.equals(that.minOrders)) return false;
        return priceCoefficient.equals(that.priceCoefficient);
    }

    @Override
    public int hashCode() {
        int result = demand.hashCode();
        result = 31 * result + minOrders.hashCode();
        result = 31 * result + priceCoefficient.hashCode();
        return result;
    }
}
