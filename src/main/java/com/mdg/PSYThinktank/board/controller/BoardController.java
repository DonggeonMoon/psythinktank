package com.mdg.PSYThinktank.board.controller;

import com.mdg.PSYThinktank.board.model.Board;
import com.mdg.PSYThinktank.board.service.BoardService;
import com.mdg.PSYThinktank.comment.service.CommentService;
import com.mdg.PSYThinktank.member.model.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;


    @GetMapping("/boardList")
    public String boardList(Pageable pageable, Model model) {
        Page<Board> boards = boardService.selectAllBoard(pageable.getPageNumber());

        int startNumber = (pageable.getPageNumber() / pageable.getPageSize()) * pageable.getPageSize() + 1;
        int endNumber =
                (pageable.getPageNumber() / pageable.getPageSize()) * pageable.getPageSize() + pageable.getPageSize();

        List<Pageable> pages = IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(i -> boards.getPageable().withPage(i))
                .collect(Collectors.toList());

        model.addAttribute("boards", boards);
        model.addAttribute("pages", pages);
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
