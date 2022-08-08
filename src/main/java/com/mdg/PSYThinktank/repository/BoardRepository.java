package com.mdg.PSYThinktank.repository;

import com.mdg.PSYThinktank.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	List<Board> findByBoardTitleContainingOrderByBoardNoDesc(String boardTitle);

    List<Board> findByBoardContentContainingOrderByBoardNoDesc(String boardContent);

    List<Board> findByMemberIdOrderByBoardNoDesc(String memberId);
}