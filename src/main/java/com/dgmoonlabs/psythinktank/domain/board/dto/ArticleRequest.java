package com.dgmoonlabs.psythinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.model.Article;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public record ArticleRequest(
        Long id,
        @NotNull(message = "boardId가 누락되었습니다.") Long boardId,
        @NotBlank(message = "memberId가 누락되었습니다.") String memberId,
        @NotBlank(message = "제목을 입력해주세요.") String title,
        @NotBlank(message = "내용을 입력해주세요.") String content,
        Boolean isNotice
) {
    public ArticleRequest {
        if (Objects.isNull(isNotice)) {
            isNotice = false;
        }
    }

    public Article toEntity() {
        return Article.builder()
                .id(id)
                .board(Board
                        .builder()
                        .id(boardId)
                        .build()
                ).memberId(memberId)
                .title(title)
                .content(content)
                .isNotice(isNotice)
                .build();
    }
}
