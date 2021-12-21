package com.mdg.PSYThinktank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdg.PSYThinktank.model.StockInfo;

public interface StockInfoRepository extends JpaRepository<StockInfo, String> {
	List<StockInfo> findAllStockInfoByStockCode(String stockCode);
	List<StockInfo> findByStockNameLike(String stockName);
	List<StockInfo> findByStockCodeLike(String stockCode);
}
