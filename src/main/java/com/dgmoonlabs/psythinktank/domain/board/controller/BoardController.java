package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.domain.board.service.BoardService;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.global.constant.QueryParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/boardList")
    public String getBoards(Pageable pageable, Model model) {
        model.addAttribute(BOARDS_KEY.getText(), boardService.selectBoards(pageable));
        return BOARD_LIST.getText();
    }

    @GetMapping("/board")
    public String getBoard(long id, Model model) {
        boardService.addHit(id);
        model.addAttribute(BOARD_KEY.getText(), boardService.selectBoard(id));
        model.addAttribute(COMMENTS_KEY.getText(), commentService.selectCommentsByBoardId(id));
        return BOARD.getText();
    }

    @GetMapping("/insertBoard")
    public String getAddBoardForm() {
        return INSERT_BOARD.getText();
    }

    @PostMapping("/board")
    public String insertBoard(BoardRequest boardRequest) {
        boardService.addBoard(boardRequest);
        return BOARD_LIST.redirect();
    }

    @GetMapping("/updateBoard")
    public String getModifyBoardForm(long id, Model model) {
        model.addAttribute(BOARD_KEY.getText(), boardService.selectBoard(id));
        return UPDATE_BOARD.getText();
    }

    @PutMapping("/board")
    public String updateBoard(BoardRequest boardRequest) {
        boardService.updateBoard(boardRequest);
        return BOARD.redirect() + QueryParameter.addParameter(QueryParameter.ID, boardRequest.id());
    }

    @DeleteMapping("/board")
    public String deleteBoard(BoardRequest boardRequest) {
        boardService.deleteBoard(boardRequest.id());
        return BOARD_LIST.redirect();
    }
}
