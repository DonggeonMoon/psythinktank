package com.mdg.PSYThinktank.stock.repository;

import com.mdg.PSYThinktank.stock.model.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository extends JpaRepository<Dividend, String> {
}
