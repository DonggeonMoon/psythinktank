package com.dgmoonlabs.psythinktank.domain.stock.comment.dto;

import com.dgmoonlabs.psythinktank.domain.stock.comment.model.StockComment;

import java.time.LocalDateTime;

public record StockCommentResponse(
        Long stockId,
        Long id,
        Long parent,
        int depth,
        long sequence,
        String memberId,
        String content,
        LocalDateTime createdAt
) {
    public static StockCommentResponse from(StockComment comment) {
        return new StockCommentResponse(
                comment.getStockId(),
                comment.getId(),
                comment.getParent(),
                comment.getDepth(),
                comment.getSequence(),
                comment.getMemberId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
