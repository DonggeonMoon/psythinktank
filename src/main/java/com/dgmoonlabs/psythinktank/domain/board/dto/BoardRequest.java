package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public record BoardRequest(
        Long id,
        @NotBlank(message = "memberId가 누락되었습니다.") String memberId,
        @NotBlank(message = "제목을 입력해주세요.") String title,
        @NotBlank(message = "내용을 입력해주세요.") String content,
        Boolean isNotice
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
                .build();
    }
}
