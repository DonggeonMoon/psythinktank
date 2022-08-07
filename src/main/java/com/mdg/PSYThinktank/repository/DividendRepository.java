package com.mdg.PSYThinktank.repository;

import com.mdg.PSYThinktank.model.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository extends JpaRepository<Dividend, String> {
}
