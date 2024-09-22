package com.dgmoonlabs.psythinktank.domain.stock.comment.dto;

import com.dgmoonlabs.psythinktank.domain.stock.comment.model.StockComment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record StockCommentRequest(
        Long id,
        @NotNull(message = "stockId가 누락되었습니다.") Long stockId,
        @NotNull(message = "parent가 누락되었습니다.") Long parent,
        int depth,
        long sequence,
        @NotBlank(message = "로그인 후 이용해주세요.") String memberId,
        @NotBlank(message = "댓글을 입력해주세요.") String content,
        LocalDateTime createdAt
) {
    public StockComment toEntity() {
        return StockComment.builder()
                .stockId(stockId)
                .id(id)
                .parent(parent)
                .depth(depth)
                .sequence(sequence)
                .memberId(memberId)
                .content(content)
                .build();
    }

    public StockCommentRequest withMemberId(String memberId) {
        return new StockCommentRequest(
                id,
                stockId,
                parent,
                depth,
                sequence,
                memberId,
                content,
                createdAt
        );
    }
}
