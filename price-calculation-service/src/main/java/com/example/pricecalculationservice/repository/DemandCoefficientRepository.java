package com.example.pricecalculationservice.repository;

import com.example.pricecalculationservice.entity.DemandCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DemandCoefficientRepository extends JpaRepository<DemandCoefficient, Integer> {
    @Query("SELECT d FROM DemandCoefficient d " +
            "WHERE d.minOrders <= :currentOrders " +
            "ORDER BY d.minOrders DESC LIMIT 1")
    Optional<DemandCoefficient> findHighestMatchingCoefficient(Integer currentOrders);
}
