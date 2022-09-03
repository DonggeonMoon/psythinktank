package com.mdg.PSYThinktank.controller;

import com.mdg.PSYThinktank.model.Comment;
import com.mdg.PSYThinktank.model.Member;
import com.mdg.PSYThinktank.service.CommentService;
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
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(comment.getMemberId())) {
                commentService.addComment(comment);
                return "redirect:/board?boardNo=" + comment.getBoardNo();
            } else {
                return "redirect:/login";
            }
        }
    }

    @PutMapping("comment")
    public String updateComment(HttpSession session, Comment comment) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(comment.getMemberId())) {
                commentService.updateComment(comment);
                return "redirect:/board?boardNo=" + comment.getBoardNo();
            } else {
                return "redirect:/login";
            }
        }
    }

    @DeleteMapping("comment")
    public String deleteComment(HttpSession session, int commentNo, int boardNo) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(commentService.selectOneComment(commentNo).getMemberId())) {
                commentService.deleteComment(commentNo);
                return "redirect:/board?boardNo=" + boardNo;
            } else {
                return "redirect:/login";
            }
        }
    }
}
