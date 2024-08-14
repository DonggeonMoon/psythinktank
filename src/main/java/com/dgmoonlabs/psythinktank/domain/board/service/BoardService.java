package com.dgmoonlabs.psythinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardResponse;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.repository.BoardRepository;
import com.dgmoonlabs.psythinktank.global.exception.BoardNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long createBoard(BoardRequest request) {
        return boardRepository.save(request.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long id) {
        return BoardResponse.from(
                boardRepository.findById(id)
                        .orElseThrow(BoardNotExistException::new)
        );
    }

    @Transactional
    public void updateBoard(BoardRequest request) {
        Board board = boardRepository.findById(request.id())
                .orElseThrow(BoardNotExistException::new);
        board.update(request);
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
