package com.dgmoonlabs.psythinktank.domain.stock.repository;

import com.dgmoonlabs.psythinktank.domain.stock.model.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {
    List<StockInfo> findByNameContains(String name);

    List<StockInfo> findBySymbolContains(String symbol);

    Optional<StockInfo> findBySymbol(String symbol);
}
