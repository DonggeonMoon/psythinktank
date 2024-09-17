package com.dgmoonlabs.psythinktank.domain.comment.dto;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CommentRequest(
        Long id,
        @NotNull(message = "articleId가 누락되었습니다.") Long articleId,
        @NotNull(message = "parent가 누락되었습니다.") Long parent,
        int depth,
        long sequence,
        @NotBlank(message = "로그인 후 이용해주세요.") String memberId,
        @NotBlank(message = "댓글을 입력해주세요.") String content,
        LocalDateTime createdAt
) {
    public Comment toEntity() {
        return Comment.builder()
                .articleId(articleId)
                .id(id)
                .parent(parent)
                .depth(depth)
                .sequence(sequence)
                .memberId(memberId)
                .content(content)
                .build();
    }

    public CommentRequest withMemberId(String memberId) {
        return new CommentRequest(
                id,
                articleId,
                parent,
                depth,
                sequence,
                memberId,
                content,
                createdAt
        );
    }
}
