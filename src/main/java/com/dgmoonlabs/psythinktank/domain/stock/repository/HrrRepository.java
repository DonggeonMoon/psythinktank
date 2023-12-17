package com.dgmoonlabs.psythinktank.domain.stock.repository;

import com.dgmoonlabs.psythinktank.domain.stock.model.Hrr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HrrRepository extends JpaRepository<Hrr, Long> {
    Optional<Hrr> findBySymbolAndBusinessYearAndReportCode(String symbol, String businessYear, String reportCode);
}
