package com.example.pricecalculationservice.repository;

import com.example.pricecalculationservice.entity.RideFare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideFareRepository extends JpaRepository<RideFare, Integer> {
    Optional<RideFare> findByFareName(String fareName);
}
