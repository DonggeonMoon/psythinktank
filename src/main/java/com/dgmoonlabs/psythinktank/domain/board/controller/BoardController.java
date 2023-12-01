package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.service.BoardService;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberDto;
import com.dgmoonlabs.psythinktank.global.constant.QueryParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/boardList")
    public String getBoards(Pageable pageable, Model model) {
        Page<Board> boards = boardService.selectBoards(pageable.getPageNumber());
        model.addAttribute(BOARDS_KEY.getText(), boards);
        return BOARD_LIST.getText();
    }

    @PostMapping("/searchByBoardTitle")
    @ResponseBody
    public Map<String, Object> searchBoardByTitle(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put(AJAX_RESPONSE_KEY.getText(), boardService.searchBoardByTitle(searchText));
        return map;
    }

    @PostMapping("/searchByBoardContent")
    @ResponseBody
    public Map<String, Object> searchBoardByContent(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put(AJAX_RESPONSE_KEY.getText(), boardService.searchBoardByContent(searchText));
        return map;
    }

    @PostMapping("/searchByMemberId")
    @ResponseBody
    public Map<String, Object> searchBoardByMemberId(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put(AJAX_RESPONSE_KEY.getText(), boardService.searchBoardByMemberId(searchText));
        return map;
    }

    @GetMapping("/board")
    public String getBoard(long id, Model model) {
        boardService.addHit(id);
        model.addAttribute(BOARD_KEY.getText(), boardService.selectBoards(id));
        model.addAttribute(COMMENTS_KEY.getText(), commentService.selectCommentsByBoardId(id));
        return BOARD.getText();
    }

    @GetMapping("/insertBoard")
    public String getAddBoardForm(HttpSession session) {
        if (session.getAttribute(SESSION_KEY.getText()) == null) {
            return LOGIN.redirect();
        }
        return INSERT_BOARD.getText();
    }

    @PostMapping("/board")
    public String insertBoard(HttpSession session, Board board) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                boardService.addBoard(board);
                return BOARD_LIST.redirect();
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @GetMapping("/updateBoard")
    public String getModifyBoardForm(HttpSession session, long id, Model model) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            Board board = boardService.selectBoards(id);
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                model.addAttribute(BOARD_KEY.getText(), boardService.selectBoards(id));
                return UPDATE_BOARD.getText();
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @PutMapping("/board")
    public String updateBoard(HttpSession session, Board board) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                boardService.updateBoard(board);
                return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, board.getId());
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @DeleteMapping("/board")
    public String deleteBoard(HttpSession session, long id, String memberId) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(memberId)) {
                boardService.deleteBoard(id);
                return BOARD_LIST.redirect();
            } else {
                return LOGIN.redirect();
            }
        }
    }
}
