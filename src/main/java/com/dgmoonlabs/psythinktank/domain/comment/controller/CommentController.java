package com.dgmoonlabs.psythinktank.domain.comment.controller;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberDto;
import com.dgmoonlabs.psythinktank.global.constant.QueryParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.SESSION_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.BOARD;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.LOGIN;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("comment")
    public String insertComment(HttpSession session, Comment comment) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(comment.getMemberId())) {
                commentService.addComment(comment);
                return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, comment.getBoardId());
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @PutMapping("comment")
    public String updateComment(HttpSession session, Comment comment) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(comment.getMemberId())) {
                commentService.updateComment(comment);
                return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, comment.getBoardId());
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @DeleteMapping("comment")
    public String deleteComment(HttpSession session, long id, long boardId) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(commentService.selectComment(id).getMemberId())) {
                commentService.deleteComment(id);
                return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, boardId);
            } else {
                return LOGIN.redirect();
            }
        }
    }
}
