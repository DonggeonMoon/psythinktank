package com.mdg.PSYThinktank.stock.repository;

import com.mdg.PSYThinktank.stock.model.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, String> {
    List<Share> findByStockCode(String stockCode);
}
