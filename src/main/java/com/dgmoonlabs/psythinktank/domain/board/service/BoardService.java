package com.dgmoonlabs.psythinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.repository.BoardRepository;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void insertOneBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public Page<Board> selectAllBoard(int page) {
        return boardRepository.findAll(PageRequest.of(page, Pagination.SIZE.getValue(), Sort.by(CriteriaField.IS_NOTICE.getName()).descending().and(Sort.by(CriteriaField.ID.getName()).descending())));
    }

    @Transactional
    public Board selectOneBoard(Long id) {
        return boardRepository.findById(id).orElse(null);
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
    public void updateOneBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public void deleteOneBoard(long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void addHit(long id) {
        Board board = boardRepository.findById(id).orElse(new Board());
        int currentHit = board.getHit();
        board.setHit(++currentHit);
        boardRepository.save(board);
    }
}
