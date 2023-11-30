package com.dgmoonlabs.psythinktank.domain.board.repository;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContainingOrderByIdDesc(String title);

    List<Board> findByContentContainingOrderByIdDesc(String content);

    List<Board> findByMemberIdOrderByIdDesc(String memberId);
}