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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    public static final String SEARCH_BY_CONTENT_TEXT = "내용1";
    public static final String SEARCH_BY_MEMBER_ID_TEXT = "회원1";
    public static final String SEARCH_BY_TITLE_TEXT = "제목1";
    public static final BoardSearchRequest BOARD_SEARCH_BY_CONTENT_REQUEST = new BoardSearchRequest(SEARCH_BY_CONTENT_TEXT);
    public static final BoardSearchRequest BOARD_SEARCH_BY_MEMBER_ID_REQUEST = new BoardSearchRequest(SEARCH_BY_MEMBER_ID_TEXT);
    public static final BoardSearchRequest BOARD_SEARCH_BY_TITLE_REQUEST = new BoardSearchRequest(SEARCH_BY_TITLE_TEXT);
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
    public static final BoardSearchResponse BOARD_SEARCH_RESPONSE = new BoardSearchResponse(List.of(BoardResponse.from(BOARD_1)));
    private static final List<Board> BOARDS = List.of(BOARD_1);
    private static final Page<Board> BOARD_PAGE = new PageImpl<>(BOARDS);
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
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
        when(boardRepository.findByTitleContainingOrderByIdDesc(anyString())).thenReturn(BOARDS);

        assertThat(boardService.searchBoardByTitle(BOARD_SEARCH_BY_TITLE_REQUEST))
                .isEqualTo(BOARD_SEARCH_RESPONSE);
    }

    @Test
    void searchBoardByContent() {
        when(boardRepository.findByContentContainingOrderByIdDesc(anyString())).thenReturn(BOARDS);

        assertThat(boardService.searchBoardByContent(BOARD_SEARCH_BY_CONTENT_REQUEST))
                .isEqualTo(BOARD_SEARCH_RESPONSE);
    }

    @Test
    void searchBoardByMemberId() {
        when(boardRepository.findByMemberIdOrderByIdDesc(anyString())).thenReturn(BOARDS);

        assertThat(boardService.searchBoardByMemberId(BOARD_SEARCH_BY_MEMBER_ID_REQUEST))
                .isEqualTo(BOARD_SEARCH_RESPONSE);
    }

    @Test
    void addHit() {
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(BOARD_2));

        int boardHit = BOARD_2.getHit();

        boardService.addHit(BOARD_2.getId());

        assertThat(BOARD_2.getHit())
                .isEqualTo(boardHit + 1);
    }
}