package com.dgmoonlabs.psythinktank.domain.stock.repository;

import com.dgmoonlabs.psythinktank.domain.stock.model.HRR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HRRRepository extends JpaRepository<HRR, Long> {
    Optional<HRR> findBySymbolAndBusinessYearAndReportCode(String symbol, String businessYear, String reportCode);
}
