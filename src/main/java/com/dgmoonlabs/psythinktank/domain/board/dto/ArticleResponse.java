package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Article;
import com.dgmoonlabs.psythinktank.global.constant.DateTimeFormat;

public record ArticleResponse(
        Long id,
        Long boardId,
        String memberId,
        String title,
        String content,
        int hit,
        Boolean isNotice,
        String createdAt
) {
    public static ArticleResponse from(final Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getBoard().getId(),
                article.getMemberId(),
                article.getTitle(),
                article.getContent(),
                article.getHit(),
                article.getIsNotice(),
                article.getCreatedAt().toLocalDateTime().format(DateTimeFormat.DATE_TIME.getFormatter())
        );
    }
}
