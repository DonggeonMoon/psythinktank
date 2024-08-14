package com.dgmoonlabs.psythinktank.domain.board.controller;

import com.dgmoonlabs.psythinktank.domain.board.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.ARTICLES_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.ARTICLE_LIST;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final ArticleService articleService;

    @GetMapping("/boards/{boardId}/articles")
    public String getBoardArticles(
            @PathVariable Long boardId,
            Pageable pageable,
            Model model
    ) {
        model.addAttribute(ARTICLES_KEY.getText(), articleService.selectBoardArticles(boardId, pageable));
        return ARTICLE_LIST.getText();
    }
}
