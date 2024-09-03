package com.dgmoonlabs.psythinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardResponse;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.repository.BoardRepository;
import com.dgmoonlabs.psythinktank.global.exception.BoardNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MessageSource messageSource;


    @Transactional
    public Long createBoard(BoardRequest request) {
        return boardRepository.save(request.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotExistException::new);
        return BoardResponse.from(
                board
        ).withName(
                messageSource.getMessage("menu.board" + board.getId(), null, LocaleContextHolder.getLocale())
        );
    }

    @Transactional
    public void updateBoard(BoardRequest request) {
        boardRepository.findById(request.id())
                .orElseThrow(BoardNotExistException::new)
                .update(request);
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
