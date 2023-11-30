package com.dgmoonlabs.psythinktank.domain.comment.repository;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM COMMENT WHERE BOARD_NO = ?1 ORDER BY CASE WHEN COMMENT_PARENT = 0 THEN COMMENT_NO ELSE COMMENT_PARENT END, COMMENT_SEQ;", nativeQuery = true)
    List<Comment> findAllById(long id);
}
