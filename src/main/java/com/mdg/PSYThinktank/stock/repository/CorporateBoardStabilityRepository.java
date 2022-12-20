package com.mdg.PSYThinktank.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdg.PSYThinktank.stock.model.CorporateBoardStability;

public interface CorporateBoardStabilityRepository extends JpaRepository<CorporateBoardStability, Long> {
    Optional<CorporateBoardStability> findByStockCode(String stockCode);
}
