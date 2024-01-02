package com.dgmoonlabs.psythinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardResponse;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.BoardSearchResponse;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.repository.BoardRepository;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Page<Board> selectBoards(Pageable pageable) {
        return boardRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.IS_NOTICE.getName())
                                .descending()
                                .and(Sort.by(CriteriaField.ID.getName())
                                        .descending())
                )
        );
    }

    @Transactional(readOnly = true)
    public BoardResponse selectBoard(long id) {
        return BoardResponse.from(
                boardRepository.findById(id)
                        .orElseThrow(IllegalStateException::new)
        );
    }

    @Transactional(readOnly = true)
    public BoardSearchResponse searchBoardByTitle(BoardSearchRequest boardSearchRequest) {
        return new BoardSearchResponse(
                boardRepository
                        .findByTitleContainingOrderByIdDesc(boardSearchRequest.searchText())
                        .stream()
                        .map(BoardResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public BoardSearchResponse searchBoardByContent(BoardSearchRequest boardSearchRequest) {
        return new BoardSearchResponse(
                boardRepository.findByContentContainingOrderByIdDesc(boardSearchRequest.searchText())
                        .stream()
                        .map(BoardResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public BoardSearchResponse searchBoardByMemberId(BoardSearchRequest boardSearchRequest) {
        return new BoardSearchResponse(
                boardRepository.findByMemberIdOrderByIdDesc(boardSearchRequest.searchText())
                        .stream()
                        .map(BoardResponse::from)
                        .toList()
        );
    }

    @Transactional
    public void addBoard(BoardRequest request) {
        boardRepository.save(request.toEntity());
    }

    @Transactional
    public void updateBoard(BoardRequest request) {
        boardRepository.findById(request.id())
                .orElseThrow(IllegalArgumentException::new)
                .update(request);
    }

    @Transactional
    public void deleteBoard(long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void addHit(long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(IllegalStateException::new);
        int currentHit = board.getHit();
        board.setHit(++currentHit);
    }
}
