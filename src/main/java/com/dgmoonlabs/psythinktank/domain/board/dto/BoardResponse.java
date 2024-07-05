package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.global.constant.DateTimeFormat;

public record BoardResponse(
        Long id,
        String memberId,
        String title,
        String content,
        int hit,
        Boolean isNotice,
        String createdAt
) {
    public static BoardResponse from(final Board board) {
        return new BoardResponse(
                board.getId(),
                board.getMemberId(),
                board.getTitle(),
                board.getContent(),
                board.getHit(),
                board.getIsNotice(),
                board.getCreatedAt().toLocalDateTime().format(DateTimeFormat.DATE_TIME.getFormatter())
        );
    }
}
