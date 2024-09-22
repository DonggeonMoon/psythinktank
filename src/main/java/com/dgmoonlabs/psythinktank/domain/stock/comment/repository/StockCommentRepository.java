package com.dgmoonlabs.psythinktank.domain.stock.comment.repository;

import com.dgmoonlabs.psythinktank.domain.stock.comment.model.StockComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockCommentRepository extends JpaRepository<StockComment, Long> {
    @Query(value = "SELECT * FROM STOCK_COMMENT WHERE STOCK_ID = ?1 ORDER BY CASE WHEN PARENT = 0 THEN id ELSE PARENT END, SEQUENCE;", nativeQuery = true)
    List<StockComment> findByArticleId(long articleId);
}
