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
    public void insertOneComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public List<Comment> selectAllCommentByBoardNo(int boardNo) {
        return commentRepository.findAllByBoardNo(boardNo);
    }

    @Transactional
    public Comment selectOneComment(int commentNo) {
        return commentRepository.findById(commentNo).orElse(null);
    }

    @Transactional
    public void updateOneComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteOneComment(int commentNo) {
        commentRepository.deleteById(commentNo);
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
    public void deleteComment(int comment_no) {
        commentRepository.deleteById(comment_no);
    }
}
