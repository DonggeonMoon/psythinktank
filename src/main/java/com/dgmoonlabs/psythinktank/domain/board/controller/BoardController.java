package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.service.BoardService;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/boardList")
    public String getBoards(Pageable pageable, Model model) {
        Page<Board> boards = boardService.selectAllBoard(pageable.getPageNumber());

        model.addAttribute("boards", boards);
        return "boardList";
    }

    @PostMapping("/searchByBoardTitle")
    @ResponseBody
    public Map<String, Object> searchBoardByTitle(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", boardService.searchBoardByTitle(searchText));
        return map;
    }

    @PostMapping("/searchByBoardContent")
    @ResponseBody
    public Map<String, Object> searchBoardByContent(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", boardService.searchBoardByContent(searchText));
        return map;
    }

    @PostMapping("/searchByMemberId")
    @ResponseBody
    public Map<String, Object> searchBoardByMemberId(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", boardService.searchBoardByMemberId(searchText));
        return map;
    }

    @GetMapping("/board")
    public String getBoard(long id, Model model) {
        boardService.addHit(id);
        model.addAttribute("board", boardService.selectOneBoard(id));
        model.addAttribute("commentList", commentService.selectAllCommentByBoardId(id));
        return "viewBoard";
    }

    @GetMapping("/insertBoard")
    public String getAddBoardForm(HttpSession session) {
        if (session.getAttribute("member") == null) {
            return "redirect:/login";
        }
        return "insertBoard";
    }

    @PostMapping("/board")
    public String insertBoard(HttpSession session, Board board) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                boardService.insertOneBoard(board);
                return "redirect:/boardList";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/updateBoard")
    public String getModifyBoardForm(HttpSession session, long id, Model model) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            Board board = boardService.selectOneBoard(id);
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                model.addAttribute("board", boardService.selectOneBoard(id));
                return "updateBoard";
            } else {
                return "redirect:/login";
            }
        }
    }

    @PutMapping("/board")
    public String updateBoard(HttpSession session, Board board) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                boardService.updateOneBoard(board);
                return "redirect:/board?id=" + board.getId();
            } else {
                return "redirect:/login";
            }
        }
    }

    @DeleteMapping("/board")
    public String deleteBoard(HttpSession session, int id, String memberId) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(memberId)) {
                boardService.deleteOneBoard(id);
                return "redirect:/boardList";
            } else {
                return "redirect:/login";
            }
        }
    }
}
