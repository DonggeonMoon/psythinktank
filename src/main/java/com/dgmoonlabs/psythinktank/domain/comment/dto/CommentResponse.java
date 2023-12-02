package com.dgmoonlabs.psythinktank.domain.comment.dto;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;

import java.sql.Timestamp;

public record CommentResponse(
        Long boardId,
        Long id,
        Long parent,
        int depth,
        long sequence,
        String memberId,
        String content,
        Timestamp createdAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getBoardId(),
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
