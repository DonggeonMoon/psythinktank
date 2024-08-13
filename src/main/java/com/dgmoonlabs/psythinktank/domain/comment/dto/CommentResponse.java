package com.dgmoonlabs.psythinktank.domain.comment.dto;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long articleId,
        Long id,
        Long parent,
        int depth,
        long sequence,
        String memberId,
        String content,
        LocalDateTime createdAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getArticleId(),
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
