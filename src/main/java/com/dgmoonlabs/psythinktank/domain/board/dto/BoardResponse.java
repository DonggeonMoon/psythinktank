package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;

import java.sql.Timestamp;

public record BoardResponse(
        Long id,
        String memberId,
        String title,
        String content,
        int hit,
        Boolean isNotice,
        Timestamp createdAt
) {
    public static BoardResponse from(final Board board) {
        return new BoardResponse(
                board.getId(),
                board.getMemberId(),
                board.getTitle(),
                board.getContent(),
                board.getHit(),
                board.getIsNotice(),
                board.getCreatedAt()
        );
    }
}
