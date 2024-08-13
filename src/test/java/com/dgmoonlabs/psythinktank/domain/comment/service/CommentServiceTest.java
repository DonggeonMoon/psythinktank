package com.dgmoonlabs.psythinktank.domain.comment.service;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;
import com.dgmoonlabs.psythinktank.domain.comment.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    private static final long CIRCULAR_ID = 1L;
    private static final long ARTICLE_ID = 1L;
    private static final Comment COMMENT = Comment.builder()
            .id(CIRCULAR_ID)
            .build();
    private static final List<Comment> COMMENTS = List.of(COMMENT);
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;

    @Test
    void selectCommentsByArticleId() {
        when(commentRepository.findAllById(anyLong()))
                .thenReturn(COMMENTS);

        assertThat(commentService.selectCommentsByArticleId(ARTICLE_ID))
                .isEqualTo(COMMENTS);
    }
}