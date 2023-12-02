package com.dgmoonlabs.psythinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.repository.BoardRepository;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Page<Board> selectBoards(int page) {
        return boardRepository.findAll(
                PageRequest.of(
                        page,
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.IS_NOTICE.getName())
                                .descending()
                                .and(Sort.by(CriteriaField.ID.getName())
                                        .descending())
                )
        );
    }

    @Transactional
    public Board selectBoards(long id) {
        return boardRepository.findById(id)
                .orElseThrow(IllegalStateException::new);
    }

    @Transactional
    public List<Board> searchBoardByTitle(String title) {
        return boardRepository.findByTitleContainingOrderByIdDesc(title);
    }

    @Transactional
    public List<Board> searchBoardByContent(String content) {
        return boardRepository.findByContentContainingOrderByIdDesc(content);
    }

    @Transactional
    public List<Board> searchBoardByMemberId(String memberId) {
        return boardRepository.findByMemberIdOrderByIdDesc(memberId);
    }

    @Transactional
    public void addBoard(BoardRequest boardRequest) {
        boardRepository.save(boardRequest.toEntity());
    }

    @Transactional
    public void updateBoard(BoardRequest boardRequest) {
        boardRepository.save(boardRequest.toEntity());
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
        boardRepository.save(board);
    }
}
