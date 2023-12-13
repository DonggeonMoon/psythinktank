package com.dgmoonlabs.PSYThinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardResponse;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchResponse;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.repository.BoardRepository;
import com.dgmoonlabs.psythinktank.domain.board.service.BoardService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    private static final long boardId1 = 1L;
    private static final long boardId2 = 2L;
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
    private static final List<Board> boards = List.of(board1, board2);
    private static final Page<Board> boardPage = new PageImpl<>(boards);
    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    BoardService boardService;

    @Test
    void selectBoards() {
        when(boardRepository.findAll(any(Pageable.class)))
                .thenReturn(boardPage);

        assertThat(boardService.selectBoards(PAGE_REQUEST))
                .isEqualTo(boardPage);
    }

    @Test
    void selectBoard() {
        when(boardRepository.findById(anyLong()))
                .thenReturn(Optional.of(board1));

        assertThat(boardService.selectBoard(boardId1))
                .isEqualTo(BoardResponse.from(board1));
    }

    @Test
    void searchBoardByTitle() {
        assertThat(boardService.searchBoardByTitle(new BoardSearchRequest("")))
                .isEqualTo(new BoardSearchResponse(List.of()));
    }

    @Test
    void searchBoardByContent() {
        assertThat(boardService.searchBoardByContent(new BoardSearchRequest("")))
                .isEqualTo(new BoardSearchResponse(List.of()));
    }

    @Test
    void searchBoardByMemberId() {
        assertThat(boardService.searchBoardByMemberId(new BoardSearchRequest("")))
                .isEqualTo(new BoardSearchResponse(List.of()));
    }

    @Test
    void addHit() {
        Board board = board1;
        int boardHit = board.getHit();

        boardService.addHit(board.getId());

        assertThat(board.getHit())
                .isEqualTo(boardHit + 1);
    }
}