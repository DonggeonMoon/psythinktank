package com.mdg.PSYThinktank.controller;

import com.mdg.PSYThinktank.model.Board;
import com.mdg.PSYThinktank.model.Member;
import com.mdg.PSYThinktank.service.BoardService;
import com.mdg.PSYThinktank.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public String boardList(@RequestParam(defaultValue = "1") int page, Model model) {
        Page<Board> boardPage = boardService.selectAllBoard(page - 1);
        model.addAttribute("boardList", boardPage);
        model.addAttribute("currentBlock", boardPage.getNumber() / boardPage.getSize());
        return "boardList";
    }

    @PostMapping("/searchByBoardTitle")
    @ResponseBody
    public Map<String, Object> searchBoardByBoardTitle(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", boardService.searchBoardByBoardTitle(searchText));
        return map;
    }

    @PostMapping("/searchByBoardContent")
    @ResponseBody
    public Map<String, Object> searchBoardByBoardContent(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", boardService.searchBoardByBoardContent(searchText));
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
    public String viewBoard(int boardNo, Model model) {
        boardService.addHit(boardNo);
        model.addAttribute("board", boardService.selectOneBoard(boardNo));
        model.addAttribute("commentList", commentService.selectAllCommentByBoardNo(boardNo));
        return "viewBoard";
    }

    @GetMapping("/insertBoard")
    public String insertBoard(HttpSession session) {
        if (session.getAttribute("member") == null) {
            return "redirect:/login";
        }
        return "insertBoard";
    }

    @PostMapping("/board")
    public String insertBoard2(HttpSession session, Board board) {
        Member sessionInfo = (Member) session.getAttribute("member");
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
    public String updateBoard(HttpSession session, int boardNo, Model model) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            Board board = boardService.selectOneBoard(boardNo);
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                model.addAttribute("board", boardService.selectOneBoard(boardNo));
                return "updateBoard";
            } else {
                return "redirect:/login";
            }
        }
    }

    @PutMapping("/board")
    public String updateBoard2(HttpSession session, Board board) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                boardService.updateOneBoard(board);
                return "redirect:/board?boardNo=" + board.getBoardNo();
            } else {
                return "redirect:/login";
            }
        }
    }

    @DeleteMapping("/board")
    public String deleteBoard(HttpSession session, int boardNo, String memberId) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(memberId)) {
                boardService.deleteOneBoard(boardNo);
                return "redirect:/boardList";
            } else {
                return "redirect:/login";
            }
        }
    }
}
