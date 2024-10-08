package com.dgmoonlabs.psythinktank.domain.board.repository;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
