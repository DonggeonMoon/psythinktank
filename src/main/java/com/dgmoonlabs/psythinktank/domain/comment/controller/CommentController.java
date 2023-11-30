package com.dgmoonlabs.psythinktank.domain.comment.controller;

import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("comment")
    public String addComment(HttpSession session, Comment comment) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(comment.getMemberId())) {
                commentService.addComment(comment);
                return "redirect:/board?id=" + comment.getBoardId();
            } else {
                return "redirect:/login";
            }
        }
    }

    @PutMapping("comment")
    public String updateComment(HttpSession session, Comment comment) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(comment.getMemberId())) {
                commentService.updateComment(comment);
                return "redirect:/board?id=" + comment.getBoardId();
            } else {
                return "redirect:/login";
            }
        }
    }

    @DeleteMapping("comment")
    public String deleteComment(HttpSession session, int id, int boardId) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(commentService.selectOneComment(id).getMemberId())) {
                commentService.deleteComment(id);
                return "redirect:/board?id=" + boardId;
            } else {
                return "redirect:/login";
            }
        }
    }
}
