package com.dgmoonlabs.psythinktank.domain.comment.controller;

import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentRequest;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberDto;
import com.dgmoonlabs.psythinktank.global.constant.QueryParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.SESSION_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.BOARD;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.LOGIN;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("comment")
    public String insertComment(@RequestBody CommentRequest commentRequest, HttpSession session) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(commentRequest.memberId())) {
                commentService.addComment(commentRequest);
                return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, commentRequest.boardId());
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @PutMapping("comment")
    public String updateComment(@RequestBody CommentRequest commentRequest, HttpSession session) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(commentRequest.memberId())) {
                commentService.updateComment(commentRequest);
                return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, commentRequest.boardId());
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @DeleteMapping("comment")
    public String deleteComment(@RequestBody CommentRequest commentRequest, HttpSession session) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(commentService.selectComment(commentRequest.id()).getMemberId())) {
                commentService.deleteComment(commentRequest.id());
                return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, commentRequest.boardId());
            } else {
                return LOGIN.redirect();
            }
        }
    }
}
