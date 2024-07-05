package com.dgmoonlabs.psythinktank.domain.comment.dto;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;

import java.time.LocalDateTime;

public record CommentRequest(
        Long boardId,
        Long id,
        Long parent,
        int depth,
        long sequence,
        String memberId,
        String content,
        LocalDateTime createdAt
) {
    public Comment toEntity() {
        return Comment.builder()
                .boardId(boardId)
                .id(id)
                .parent(parent)
                .depth(depth)
                .sequence(sequence)
                .memberId(memberId)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}
