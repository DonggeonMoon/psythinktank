package com.mdg.PSYThinktank.repository;

import com.mdg.PSYThinktank.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	@Query(value = "SELECT * FROM COMMENT WHERE BOARD_NO = ?1 ORDER BY CASE WHEN COMMENT_PARENT = 0 THEN COMMENT_NO ELSE COMMENT_PARENT END, COMMENT_SEQ;", nativeQuery = true)
	public List<Comment> findAllByBoardNo(int BoardNo);
}
