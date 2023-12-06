package com.dgmoonlabs.PSYThinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.repository.BoardRepository;
import com.dgmoonlabs.psythinktank.domain.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    private static final Board board1 = Board.builder()
            .id(1L)
            .title("제목1")
            .content("내용1")
            .hit(0)
            .memberId("회원1")
            .isNotice(false)
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    private static final Board board2 = Board.builder()
            .id(2L)
            .title("제목2")
            .content("내용2")
            .hit(0)
            .memberId("회원2")
            .isNotice(false)
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    @InjectMocks
    BoardService boardService;
    private List<Board> boards;
    private Page<Board> boardPage;
    @Mock
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        boards = List.of(board1, board2);
        boardPage = new PageImpl<>(boards);
    }

    @Test
    void selectBoards() {
        when(boardRepository.findAll(any(Pageable.class)))
                .thenReturn(boardPage);

        assertThat(boardService.selectBoards(PAGE_REQUEST))
                .isEqualTo(boardPage);
    }

    @Test
    void selectBoard() {
    }

    @Test
    void searchBoardByTitle() {
    }

    @Test
    void searchBoardByContent() {
    }

    @Test
    void searchBoardByMemberId() {
    }

    @Test
    void addBoard() {
    }

    @Test
    void updateBoard() {
    }

    @Test
    void deleteBoard() {
    }

    @Test
    void addHit() {
    }
}