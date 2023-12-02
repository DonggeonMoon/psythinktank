package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;

import java.sql.Timestamp;

public record BoardRequest(
        Long id,
        String memberId,
        String title,
        String content,
        int hit,
        Boolean isNotice,
        Timestamp createdAt
) {
    public Board toEntity() {
        return Board.builder()
                .id(id)
                .memberId(memberId)
                .title(title)
                .hit(hit)
                .isNotice(isNotice)
                .createdAt(createdAt)
                .build();
    }
}
