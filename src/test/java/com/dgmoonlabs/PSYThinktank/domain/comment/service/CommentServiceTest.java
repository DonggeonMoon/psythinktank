package com.dgmoonlabs.PSYThinktank.domain.comment.service;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;
import com.dgmoonlabs.psythinktank.domain.comment.repository.CommentRepository;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
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
    private static final long CIRCULAR_ID_1 = 1L;
    private static final long CIRCULAR_ID_2 = 2L;
    private static final Comment COMMENT_1 = Comment.builder()
            .id(CIRCULAR_ID_1)
            .build();
    private static final Comment COMMENT_2 = Comment.builder()
            .id(CIRCULAR_ID_2)
            .build();

    private static final List<Comment> COMMENTS = List.of(COMMENT_1, COMMENT_2);

    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;

    @Test
    void selectCommentsByBoardId() {
        when(commentRepository.findAllById(anyLong()))
                .thenReturn(COMMENTS);
        assertThat(commentService.selectCommentsByBoardId(anyLong()))
                .isEqualTo(COMMENTS);
    }
}