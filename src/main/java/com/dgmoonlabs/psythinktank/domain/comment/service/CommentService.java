package com.dgmoonlabs.psythinktank.domain.comment.service;

import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentRequest;
import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;
import com.dgmoonlabs.psythinktank.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Comment> selectCommentsByArticleId(long articleId) {
        return commentRepository.findAllById(articleId);
    }

    @Transactional
    public void addComment(CommentRequest commentRequest) {
        commentRepository.save(commentRequest.toEntity());
    }

    @Transactional
    public void updateComment(CommentRequest request) {
        commentRepository.findById(request.id())
                .orElseThrow(IllegalArgumentException::new)
                .update(request);
    }

    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }
}
