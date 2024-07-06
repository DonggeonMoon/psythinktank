package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.domain.board.service.BoardService;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping
    public String getBoards(Pageable pageable, Model model) {
        model.addAttribute(BOARDS_KEY.getText(), boardService.selectBoards(pageable));
        return BOARD_LIST.getText();
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable long id, Model model) {
        boardService.addHit(id);
        model.addAttribute(BOARD_KEY.getText(), boardService.selectBoard(id));
        model.addAttribute(COMMENTS_KEY.getText(), commentService.selectCommentsByBoardId(id));
        return VIEW_BOARD.getText();
    }

    @GetMapping("/add")
    public String getAddBoardForm() {
        return INSERT_BOARD.getText();
    }

    @PostMapping
    public String insertBoard(@Valid BoardRequest boardRequest) {
        boardService.addBoard(boardRequest);
        return "redirect:/boards";
    }

    @GetMapping("/modify/{id}")
    public String getModifyBoardForm(@PathVariable long id, Model model) {
        model.addAttribute(BOARD_KEY.getText(), boardService.selectBoard(id));
        return UPDATE_BOARD.getText();
    }

    @PutMapping
    public String updateBoard(@Valid BoardRequest boardRequest) {
        boardService.updateBoard(boardRequest);
        return "redirect:/boards/" + boardRequest.id();
    }

    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable long id) {
        boardService.deleteBoard(id);
        return "redirect:/boards";
    }
}
