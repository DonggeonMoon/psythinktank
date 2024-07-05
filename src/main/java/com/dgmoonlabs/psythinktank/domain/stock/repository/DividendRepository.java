package com.dgmoonlabs.psythinktank.domain.stock.repository;

import com.dgmoonlabs.psythinktank.domain.stock.model.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DividendRepository extends JpaRepository<Dividend, Long> {
    Optional<Dividend> findBySymbol(String symbol);
}
