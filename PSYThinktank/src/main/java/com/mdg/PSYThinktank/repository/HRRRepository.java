package com.mdg.PSYThinktank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdg.PSYThinktank.model.HRR;

public interface HRRRepository extends JpaRepository<HRR, Integer>{
	List<HRR> findByStockCode(String stockCode);
	HRR findByStockCodeAndBsnsYearAndReprtCode(String stockCode, String bsnsYear, String reprtCode);
}
