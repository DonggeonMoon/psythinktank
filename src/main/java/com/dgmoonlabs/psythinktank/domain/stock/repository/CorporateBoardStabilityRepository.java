package com.dgmoonlabs.psythinktank.domain.stock.repository;

import com.dgmoonlabs.psythinktank.domain.stock.model.CorporateBoardStability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorporateBoardStabilityRepository extends JpaRepository<CorporateBoardStability, Long> {
    Optional<CorporateBoardStability> findBySymbol(String symbol);
}
