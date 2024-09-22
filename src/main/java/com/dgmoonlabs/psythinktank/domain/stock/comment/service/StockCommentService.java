package com.dgmoonlabs.psythinktank.domain.stock.comment.service;

import com.dgmoonlabs.psythinktank.domain.stock.comment.dto.StockCommentRequest;
import com.dgmoonlabs.psythinktank.domain.stock.comment.dto.StockCommentResponse;
import com.dgmoonlabs.psythinktank.domain.stock.comment.repository.StockCommentRepository;
import com.dgmoonlabs.psythinktank.global.exception.CommentNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockCommentService {
    private final StockCommentRepository stockCommentRepository;

    @Transactional
    public Long createComment(StockCommentRequest stockCommentRequest) {
        return stockCommentRepository.save(stockCommentRequest.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<StockCommentResponse> getComments(long articleId) {
        return stockCommentRepository.findByArticleId(articleId)
                .stream()
                .map(StockCommentResponse::from)
                .toList();
    }

    @Transactional
    public void updateComment(StockCommentRequest request) {
        stockCommentRepository.findById(request.id())
                .orElseThrow(CommentNotExistException::new)
                .update(request);
    }

    @Transactional
    public void deleteComment(long id) {
        stockCommentRepository.deleteById(id);
    }
}
