package com.dgmoonlabs.psythinktank.domain.board.repository;

import com.dgmoonlabs.psythinktank.domain.board.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContainingOrderByIdDesc(String title);

    List<Article> findByContentContainingOrderByIdDesc(String content);

    List<Article> findByMemberIdOrderByIdDesc(String memberId);

    Page<Article> findByBoardId(Long boardId, Pageable pageable);
}