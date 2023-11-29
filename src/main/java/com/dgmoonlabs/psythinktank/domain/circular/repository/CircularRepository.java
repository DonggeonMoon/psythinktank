package com.dgmoonlabs.psythinktank.domain.circular.repository;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CircularRepository extends JpaRepository<Circular, Integer> {
}
