package com.dgmoonlabs.psythinktank.domain.stock.repository;

import com.dgmoonlabs.psythinktank.domain.stock.model.HRR;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("SpellCheckingInspection")
public interface HRRRepository extends JpaRepository<HRR, Long> {

    HRR findBySymbolAndBusinessYearAndReportCode(String stockCode, String bsnsYear, String reprtCode);
}
