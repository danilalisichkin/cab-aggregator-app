package com.cabaggregator.pricecalculationservice.repository;

import com.cabaggregator.pricecalculationservice.entity.RideFare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideFareRepository extends JpaRepository<RideFare, String> {
}
