package com.dgmoonlabs.psythinktank.domain.comment.controller;

import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentRequest;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.global.constant.QueryParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import static com.dgmoonlabs.psythinktank.global.constant.ViewName.BOARD;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public String insertComment(CommentRequest commentRequest) {
        commentService.addComment(commentRequest);
        return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, commentRequest.boardId());
    }

    @PutMapping("/comment")
    public String updateComment(CommentRequest commentRequest) {
        commentService.updateComment(commentRequest);
        return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, commentRequest.boardId());
    }

    @DeleteMapping("/comment")
    public String deleteComment(CommentRequest commentRequest) {
        commentService.deleteComment(commentRequest.id());
        return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, commentRequest.boardId());
    }
}
