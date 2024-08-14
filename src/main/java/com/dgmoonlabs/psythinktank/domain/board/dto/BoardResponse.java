package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Article;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;

import java.util.List;

public record BoardResponse(
        Long id,
        String name,
        Boolean isPublic,
        List<Article> articles
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getName(),
                board.getIsPublic(),
                board.getArticles()
        );
    }
}
