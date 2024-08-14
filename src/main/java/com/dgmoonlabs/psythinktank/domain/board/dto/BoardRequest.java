package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;

public record BoardRequest(
        Long id,
        String name,
        Boolean isPublic
) {

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .name(name)
                .isPublic(isPublic)
                .build();
    }
}
