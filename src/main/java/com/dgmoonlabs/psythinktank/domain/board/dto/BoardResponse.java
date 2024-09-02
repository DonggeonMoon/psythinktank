package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;

public record BoardResponse(
        Long id,
        String name,
        Boolean isPublic
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getName(),
                board.getIsPublic()
        );
    }
}
