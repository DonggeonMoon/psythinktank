package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;

import java.sql.Timestamp;
import java.util.Objects;

public record BoardRequest(
        Long id,
        String memberId,
        String title,
        String content,
        Boolean isNotice,
        Timestamp createdAt
) {
    public BoardRequest {
        if (Objects.isNull(isNotice)) {
            isNotice = false;
        }
    }

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .memberId(memberId)
                .title(title)
                .content(content)
                .isNotice(isNotice)
                .createdAt(createdAt)
                .build();
    }
}
