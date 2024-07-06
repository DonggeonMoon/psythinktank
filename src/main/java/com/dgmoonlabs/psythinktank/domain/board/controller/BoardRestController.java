package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchResponse;
import com.dgmoonlabs.psythinktank.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardRestController {
    private final BoardService boardService;

    @PostMapping("/search/title")
    public ResponseEntity<BoardSearchResponse> searchBoardByTitle(
            @Valid
            @RequestBody
            BoardSearchRequest boardSearchRequest
    ) {
        return ResponseEntity.ok(boardService.searchBoardByTitle(boardSearchRequest));
    }

    @PostMapping("/search/content")
    public ResponseEntity<BoardSearchResponse> searchBoardByContent(
            @Valid
            @RequestBody
            BoardSearchRequest boardSearchRequest
    ) {
        return ResponseEntity.ok(boardService.searchBoardByContent(boardSearchRequest));
    }

    @PostMapping("/search/memberId")
    public ResponseEntity<BoardSearchResponse> searchBoardByMemberId(
            @Valid
            @RequestBody
            BoardSearchRequest boardSearchRequest
    ) {
        return ResponseEntity.ok(boardService.searchBoardByMemberId(boardSearchRequest));
    }
}
