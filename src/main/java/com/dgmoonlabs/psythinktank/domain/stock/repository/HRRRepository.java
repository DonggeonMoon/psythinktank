package com.dgmoonlabs.psythinktank.domain.stock.repository;

import com.dgmoonlabs.psythinktank.domain.stock.model.HRR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public interface HRRRepository extends JpaRepository<HRR, Integer> {
    List<HRR> findByStockCode(String stockCode);

    HRR findByStockCodeAndBsnsYearAndReprtCode(String stockCode, String bsnsYear, String reprtCode);
}
