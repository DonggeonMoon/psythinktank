package com.mdg.PSYThinktank.service;

import com.mdg.PSYThinktank.model.Board;
import com.mdg.PSYThinktank.repository.BoardRepository;
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
        return boardRepository.findAll(PageRequest.of(page, 10, Sort.by("isNotice").descending().and(Sort.by("boardNo").descending())));
    }

    @Transactional
    public Board selectOneBoard(int boardNo) {
        return boardRepository.findById(boardNo).orElse(null);
    }

    @Transactional
    public List<Board> searchBoardByBoardTitle(String boardTitle) {
        return boardRepository.findByBoardTitleContainingOrderByBoardNoDesc(boardTitle);
    }

    @Transactional
    public List<Board> searchBoardByBoardContent(String boardContent) {
        return boardRepository.findByBoardContentContainingOrderByBoardNoDesc(boardContent);
    }

    @Transactional
    public List<Board> searchBoardByMemberId(String memberId) {
        return boardRepository.findByMemberIdOrderByBoardNoDesc(memberId);
    }

    @Transactional
    public void updateOneBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public void deleteOneBoard(int boardNo) {
        boardRepository.deleteById(boardNo);
    }

    @Transactional
    public void addHit(int board_no) {
        Board board = boardRepository.findById(board_no).orElse(new Board());
        board.setBoardHit(board.getBoardHit() + 1);
        boardRepository.save(board);
    }
}
