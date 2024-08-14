package com.dgmoonlabs.psythinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BoardServiceTest {
    public static final BoardRequest BOARD_REQUEST_1 = new BoardRequest(
            1L,
            "일반 게시판",
            true
    );

    public static final BoardRequest BOARD_REQUEST_2 = new BoardRequest(
            1L,
            "다른 게시판",
            true
    );
    @Autowired
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        boardService.createBoard(BOARD_REQUEST_1);
    }

    @Test
    void createBoard() {
        assertThat(boardService.getBoard(BOARD_REQUEST_1.id()).name())
                .isEqualTo(BOARD_REQUEST_1.toEntity().getName());
    }

    @Test
    void getBoard() {
        assertThat(boardService.getBoard(BOARD_REQUEST_1.id()).name())
                .isEqualTo(BOARD_REQUEST_1.toEntity().getName());
    }

    @Test
    void updateBoard() {
        boardService.updateBoard(BOARD_REQUEST_2);

        assertThat(boardService.getBoard(BOARD_REQUEST_1.id()).name())
                .isEqualTo(BOARD_REQUEST_2.toEntity().getName());
    }

    @Test
    void deleteBoard() {
        boardService.updateBoard(BOARD_REQUEST_1);

        assertThat(boardService.getBoard(BOARD_REQUEST_1.id()).name())
                .isEqualTo(BOARD_REQUEST_1.toEntity().getName());
    }
}