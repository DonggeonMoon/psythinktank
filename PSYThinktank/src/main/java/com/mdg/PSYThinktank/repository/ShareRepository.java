package com.mdg.PSYThinktank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdg.PSYThinktank.model.Share;

public interface ShareRepository extends JpaRepository<Share, String> {
	List<Share> findByStockCode(String stockCode);
}
