package com.dgmoonlabs.psythinktank.domain.comment.service;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;
import com.dgmoonlabs.psythinktank.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public List<Comment> selectAllCommentByBoardId(long boardId) {
        return commentRepository.findAllById(boardId);
    }

    @Transactional
    public Comment selectOneComment(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }
}
