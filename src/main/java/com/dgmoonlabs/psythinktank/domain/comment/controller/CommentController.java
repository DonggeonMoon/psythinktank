package com.dgmoonlabs.psythinktank.domain.comment.controller;

import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentRequest;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.global.constant.QueryParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dgmoonlabs.psythinktank.global.constant.ViewName.ARTICLE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public String createComment(CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
        return ARTICLE.redirect() + QueryParameter.addParameter(QueryParameter.ID, commentRequest.articleId());
    }

    @PutMapping
    public String updateComment(CommentRequest commentRequest) {
        commentService.updateComment(commentRequest);
        return ARTICLE.redirect() + QueryParameter.addParameter(QueryParameter.ID, commentRequest.articleId());
    }

    @DeleteMapping
    public String deleteComment(CommentRequest commentRequest) {
        commentService.deleteComment(commentRequest.id());
        return ARTICLE.redirect() + QueryParameter.addParameter(QueryParameter.ID, commentRequest.articleId());
    }
}
