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
    private static final long NEWSLETTER_ID = 1L;
    private static final long ARTICLE_ID = 1L;
    private static final Comment COMMENT = Comment.builder()
            .id(NEWSLETTER_ID)
            .build();
    private static final List<Comment> COMMENTS = List.of(COMMENT);
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;

    @Test
    void getCommentsByArticleId() {
        when(commentRepository.findAllById(anyLong()))
                .thenReturn(COMMENTS);

        assertThat(commentService.getCommentsByArticleId(ARTICLE_ID))
                .isEqualTo(COMMENTS);
    }
}