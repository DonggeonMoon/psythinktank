package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardResponse;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchResponse;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.service.BoardService;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
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
    public String getBoard(long id, Model model) {
        boardService.addHit(id);
        model.addAttribute(BOARD_KEY.getText(), boardService.selectBoard(id));
        model.addAttribute(COMMENTS_KEY.getText(), commentService.selectCommentsByBoardId(id));
        return BOARD.getText();
    }

    @GetMapping("/insertBoard")
    public String getAddBoardForm(HttpSession session) {
        return INSERT_BOARD.getText();
    }

    @PostMapping("/board")
    public String insertBoard(BoardRequest boardRequest) {
        boardService.addBoard(boardRequest);
        return BOARD_LIST.redirect();
    }

    @GetMapping("/updateBoard")
    public String getModifyBoardForm(long id, HttpSession session, Model model) {
        BoardResponse boardResponse = boardService.selectBoard(id);
        model.addAttribute(BOARD_KEY.getText(), boardResponse);
        return UPDATE_BOARD.getText();
    }

    @PutMapping("/board")
    public String updateBoard(BoardRequest boardRequest, HttpSession session) {
        boardService.updateBoard(boardRequest);
        return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, boardRequest.id());
    }

    @DeleteMapping("/board")
    public String deleteBoard(BoardRequest boardRequest) {
        boardService.deleteBoard(boardRequest.id());
        return BOARD_LIST.redirect();
    }
}
