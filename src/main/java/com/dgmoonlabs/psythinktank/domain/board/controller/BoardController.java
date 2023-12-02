package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardResponse;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchResponse;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.service.BoardService;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberResponse;
import com.dgmoonlabs.psythinktank.global.constant.QueryParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/boardList")
    public String getBoards(Pageable pageable, Model model) {
        Page<Board> boards = boardService.selectBoards(pageable);
        model.addAttribute(BOARDS_KEY.getText(), boards);
        return BOARD_LIST.getText();
    }

    @PostMapping("/searchByBoardTitle")
    @ResponseBody
    public ResponseEntity<BoardSearchResponse> searchBoardByTitle(@RequestBody BoardSearchRequest boardSearchRequest) {
        return ResponseEntity.ok(boardService.searchBoardByTitle(boardSearchRequest));
    }

    @PostMapping("/searchByBoardContent")
    @ResponseBody
    public ResponseEntity<BoardSearchResponse> searchBoardByContent(@RequestBody BoardSearchRequest boardSearchRequest) {
        return ResponseEntity.ok(boardService.searchBoardByContent(boardSearchRequest));
    }

    @PostMapping("/searchByMemberId")
    @ResponseBody
    public ResponseEntity<BoardSearchResponse> searchBoardByMemberId(@RequestBody BoardSearchRequest boardSearchRequest) {
        return ResponseEntity.ok(boardService.searchBoardByMemberId(boardSearchRequest));
    }

    @GetMapping("/board")
    public String getBoard(BoardRequest boardRequest, Model model) {
        boardService.addHit(boardRequest.id());
        model.addAttribute(BOARD_KEY.getText(), boardService.selectBoards(boardRequest));
        model.addAttribute(COMMENTS_KEY.getText(), commentService.selectCommentsByBoardId(boardRequest.id()));
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
    public String insertBoard(BoardRequest boardRequest, HttpSession session) {
        MemberResponse sessionInfo = (MemberResponse) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.memberId().equals(boardRequest.memberId())) {
                boardService.addBoard(boardRequest);
                return BOARD_LIST.redirect();
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @GetMapping("/updateBoard")
    public String getModifyBoardForm(BoardRequest boardRequest, HttpSession session, Model model) {
        MemberResponse sessionInfo = (MemberResponse) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            BoardResponse boardResponse = boardService.selectBoards(boardRequest);
            if (sessionInfo.memberId().equals(boardResponse.memberId())) {
                model.addAttribute(BOARD_KEY.getText(), boardService.selectBoards(boardRequest));
                return UPDATE_BOARD.getText();
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @PutMapping("/board")
    public String updateBoard(BoardRequest boardRequest, HttpSession session) {
        MemberResponse sessionInfo = (MemberResponse) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.memberId().equals(boardRequest.memberId())) {
                boardService.updateBoard(boardRequest);
                return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, boardRequest.id());
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @DeleteMapping("/board")
    public String deleteBoard(BoardRequest boardRequest, HttpSession session) {
        MemberResponse sessionInfo = (MemberResponse) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.memberId().equals(boardRequest.memberId())) {
                boardService.deleteBoard(boardRequest.id());
                return BOARD_LIST.redirect();
            } else {
                return LOGIN.redirect();
            }
        }
    }
}
