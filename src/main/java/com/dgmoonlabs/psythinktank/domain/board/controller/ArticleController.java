package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleRequest;
import com.dgmoonlabs.psythinktank.domain.board.service.ArticleService;
import com.dgmoonlabs.psythinktank.domain.comment.service.CommentService;
import com.dgmoonlabs.psythinktank.global.constant.ApiName;
import com.dgmoonlabs.psythinktank.global.constant.QueryParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @GetMapping("/add")
    public String getAddArticleForm(@RequestParam long boardId, Model model) {
        model.addAttribute(BOARD_ID_KEY.getText(), boardId);
        return INSERT_ARTICLE.getText();
    }

    @PostMapping
    public String insertArticle(@Valid ArticleRequest articleRequest) {
        articleService.addArticle(articleRequest);
        return ApiName.ARTICLE.redirect() + QueryParameter.addParameter(QueryParameter.BOARD_ID, articleRequest.boardId());
    }

    @GetMapping
    public String getArticles(
            @RequestParam(defaultValue = "1") long boardId,
            Pageable pageable,
            Model model
    ) {
        model.addAttribute(ARTICLES_KEY.getText(), articleService.selectBoardArticles(boardId, pageable));
        model.addAttribute(BOARD_ID_KEY.getText(), boardId);
        return ARTICLE_LIST.getText();
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable long id, Model model) {
        articleService.addHit(id);
        model.addAttribute(ARTICLE_KEY.getText(), articleService.selectArticle(id));
        model.addAttribute(COMMENTS_KEY.getText(), commentService.selectCommentsByArticleId(id));
        return VIEW_ARTICLE.getText();
    }

    @GetMapping("/modify/{id}")
    public String getModifyArticleForm(@PathVariable long id, Model model) {
        model.addAttribute(ARTICLE_KEY.getText(), articleService.selectArticle(id));
        return UPDATE_ARTICLE.getText();
    }

    @PutMapping
    public String updateArticle(@Valid ArticleRequest articleRequest) {
        articleService.updateArticle(articleRequest);
        return ApiName.ARTICLE.redirect() + "/" + articleRequest.id();
    }

    @DeleteMapping("/{id}")
    public String deleteArticle(@RequestParam long boardId, @PathVariable long id) {
        articleService.deleteArticle(id);
        return ApiName.ARTICLE.redirect() + QueryParameter.addParameter(QueryParameter.BOARD_ID, boardId);
    }
}
