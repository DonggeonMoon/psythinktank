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

    public static final String SEARCH_TEXT = "검색어";
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    public static final BoardSearchRequest BOARD_SEARCH_REQUEST = new BoardSearchRequest(SEARCH_TEXT);
    public static final BoardSearchResponse BOARD_SEARCH_RESPONSE = new BoardSearchResponse(List.of());
    private static final long BOARD_ID_1 = 1L;
    private static final long BOARD_ID_2 = 2L;
    private static final Board BOARD_1 = Board.builder()
            .id(BOARD_ID_1)
            .title("제목1")
            .content("내용1")
            .hit(0)
            .memberId("회원1")
            .isNotice(false)
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    private static final Board BOARD_2 = Board.builder()
            .id(BOARD_ID_2)
            .title("제목2")
            .content("내용2")
            .hit(0)
            .memberId("회원2")
            .isNotice(false)
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    private static final List<Board> BOARDS = List.of(BOARD_1, BOARD_2);
    private static final Page<Board> BOARD_PAGE = new PageImpl<>(BOARDS);
    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private BoardService boardService;

    @Test
    void selectBoards() {
        when(boardRepository.findAll(any(Pageable.class)))
                .thenReturn(BOARD_PAGE);

        assertThat(boardService.selectBoards(PAGE_REQUEST))
                .isEqualTo(BOARD_PAGE);
    }

    @Test
    void selectBoard() {
        when(boardRepository.findById(anyLong()))
                .thenReturn(Optional.of(BOARD_1));

        assertThat(boardService.selectBoard(BOARD_ID_1))
                .isEqualTo(BoardResponse.from(BOARD_1));
    }

    @Test
    void searchBoardByTitle() {
        assertThat(boardService.searchBoardByTitle(BOARD_SEARCH_REQUEST))
                .isEqualTo(BOARD_SEARCH_RESPONSE);
    }

    @Test
    void searchBoardByContent() {
        assertThat(boardService.searchBoardByContent(BOARD_SEARCH_REQUEST))
                .isEqualTo(BOARD_SEARCH_RESPONSE);
    }

    @Test
    void searchBoardByMemberId() {
        assertThat(boardService.searchBoardByMemberId(BOARD_SEARCH_REQUEST))
                .isEqualTo(BOARD_SEARCH_RESPONSE);
    }

    @Test
    void addHit() {
        when(boardRepository.findById(anyLong()))
                .thenReturn(Optional.of(BOARD_1));

        Board board = BOARD_1;
        int boardHit = board.getHit();

        boardService.addHit(board.getId());

        assertThat(board.getHit())
                .isEqualTo(boardHit + 1);
    }
}