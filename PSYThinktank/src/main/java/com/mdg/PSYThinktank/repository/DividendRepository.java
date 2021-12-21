package com.mdg.PSYThinktank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdg.PSYThinktank.model.Dividend;

public interface DividendRepository extends JpaRepository<Dividend, String> {
}
