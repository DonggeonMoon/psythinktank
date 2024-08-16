package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleSearchRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleSearchResponse;
import com.dgmoonlabs.psythinktank.domain.board.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleRestController {
    private final ArticleService articleService;

    @PostMapping("/search/title")
    public ResponseEntity<ArticleSearchResponse> searchArticleByTitle(
            @Valid
            @RequestBody
            ArticleSearchRequest articleSearchRequest
    ) {
        return ResponseEntity.ok(
                articleService.searchArticleByTitle(articleSearchRequest)
        );
    }

    @PostMapping("/search/content")
    public ResponseEntity<ArticleSearchResponse> searchArticleByContent(
            @Valid
            @RequestBody
            ArticleSearchRequest articleSearchRequest
    ) {
        return ResponseEntity.ok(
                articleService.searchArticleByContent(articleSearchRequest)
        );
    }

    @PostMapping("/search/memberId")
    public ResponseEntity<ArticleSearchResponse> searchArticleByMemberId(
            @Valid
            @RequestBody
            ArticleSearchRequest articleSearchRequest
    ) {
        return ResponseEntity.ok(
                articleService.searchArticleByMemberId(articleSearchRequest)
        );
    }
}
