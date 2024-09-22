package com.dgmoonlabs.psythinktank.domain.stock.comment.controller;

import com.dgmoonlabs.psythinktank.domain.stock.comment.dto.StockCommentRequest;
import com.dgmoonlabs.psythinktank.domain.stock.comment.dto.StockCommentResponse;
import com.dgmoonlabs.psythinktank.domain.stock.comment.service.StockCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks/comments")
public class StockCommentRestController {
    private final StockCommentService stockCommentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody StockCommentRequest stockCommentRequest) {
        stockCommentRequest = stockCommentRequest.withMemberId(SecurityContextHolder.getContext().getAuthentication().getName());

        return ResponseEntity.created(
                URI.create("/stocks/comment/" + stockCommentService.createComment(stockCommentRequest))
        ).build();
    }

    @GetMapping
    public ResponseEntity<List<StockCommentResponse>> getComments(@RequestParam Long stockId) {
        return ResponseEntity.ok(
                stockCommentService.getComments(stockId)
        );
    }

    @PutMapping
    public ResponseEntity<Void> updateComment(@RequestBody StockCommentRequest stockCommentRequest) {
        stockCommentService.updateComment(stockCommentRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        stockCommentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
