package com.cabaggregator.pricecalculationservice.repository;

import java.util.Optional;

public interface CellDemandStore {
    Optional<Integer> get(String gridCell);

    void set(String gridCell, int demand);

    void increment(String gridCell);
}
