package com.example.pricecalculationservice.entity;

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
}
