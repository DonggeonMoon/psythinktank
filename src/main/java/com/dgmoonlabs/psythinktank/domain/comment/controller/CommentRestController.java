package com.dgmoonlabs.psythinktank.domain.comment.controller;

import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentRequest;
import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentResponse;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.created(
                URI.create("/comment/" + commentService.createComment(commentRequest))
        ).build();
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@RequestParam Long articleId) {
        return ResponseEntity.ok(
                commentService.getComments(articleId)
        );
    }

    @PutMapping
    public ResponseEntity<Void> updateComment(@RequestBody CommentRequest commentRequest) {
        commentService.updateComment(commentRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
