package com.mdg.PSYThinktank.repository;

import com.mdg.PSYThinktank.model.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockInfoRepository extends JpaRepository<StockInfo, String> {
    List<StockInfo> findAllStockInfoByStockCode(String stockCode);

    List<StockInfo> findByStockNameLike(String stockName);

    List<StockInfo> findByStockCodeLike(String stockCode);
}
