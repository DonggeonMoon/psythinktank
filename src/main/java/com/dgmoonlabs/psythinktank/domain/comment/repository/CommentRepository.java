package com.dgmoonlabs.psythinktank.domain.comment.repository;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM COMMENT WHERE ARTICLE_ID = ?1 ORDER BY CASE WHEN PARENT = 0 THEN id ELSE PARENT END, SEQUENCE;", nativeQuery = true)
    List<Comment> findByArticleId(long articleId);
}
