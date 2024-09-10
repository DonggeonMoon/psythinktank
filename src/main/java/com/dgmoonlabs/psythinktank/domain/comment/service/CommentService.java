package com.dgmoonlabs.psythinktank.domain.comment.service;

import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentRequest;
import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentResponse;
import com.dgmoonlabs.psythinktank.domain.comment.repository.CommentRepository;
import com.dgmoonlabs.psythinktank.global.exception.CommentNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Long createComment(CommentRequest commentRequest) {
        return commentRepository.save(commentRequest.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(long articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public void updateComment(CommentRequest request) {
        commentRepository.findById(request.id())
                .orElseThrow(CommentNotExistException::new)
                .update(request);
    }

    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }
}
