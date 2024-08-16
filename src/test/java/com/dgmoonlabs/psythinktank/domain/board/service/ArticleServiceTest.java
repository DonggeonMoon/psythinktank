package com.dgmoonlabs.psythinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleResponse;
import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleSearchRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleSearchResponse;
import com.dgmoonlabs.psythinktank.domain.board.model.Article;
import com.dgmoonlabs.psythinktank.domain.board.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    public static final String SEARCH_BY_CONTENT_TEXT = "내용1";
    public static final String SEARCH_BY_MEMBER_ID_TEXT = "회원1";
    public static final String SEARCH_BY_TITLE_TEXT = "제목1";
    public static final ArticleSearchRequest ARTICLE_SEARCH_BY_CONTENT_REQUEST = new ArticleSearchRequest(SEARCH_BY_CONTENT_TEXT);
    public static final ArticleSearchRequest ARTICLE_SEARCH_BY_MEMBER_ID_REQUEST = new ArticleSearchRequest(SEARCH_BY_MEMBER_ID_TEXT);
    public static final ArticleSearchRequest ARTICLE_SEARCH_BY_TITLE_REQUEST = new ArticleSearchRequest(SEARCH_BY_TITLE_TEXT);
    private static final long ARTICLE_ID_1 = 1L;
    private static final long ARTICLE_ID_2 = 2L;
    private static final Article ARTICLE_1 = Article.builder()
            .id(ARTICLE_ID_1)
            .title("제목1")
            .content("내용1")
            .hit(0)
            .memberId("회원1")
            .isNotice(false)
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    private static final Article ARTICLE_2 = Article.builder()
            .id(ARTICLE_ID_2)
            .title("제목2")
            .content("내용2")
            .hit(0)
            .memberId("회원2")
            .isNotice(false)
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    private static final ArticleResponse ARTICLE_RESPONSE_1 = ArticleResponse.from(ARTICLE_1);
    private static final ArticleSearchResponse ARTICLE_SEARCH_RESPONSE = new ArticleSearchResponse(List.of(ArticleResponse.from(ARTICLE_1)));
    private static final List<Article> ARTICLES = List.of(ARTICLE_1);
    private static final List<ArticleResponse> ARTICLE_RESPONSES = List.of(ARTICLE_RESPONSE_1);
    private static final Page<Article> ARTICLE_PAGE = new PageImpl<>(ARTICLES);
    private static final Page<ArticleResponse> ARTICLE_RESPONSE_PAGE = new PageImpl<>(ARTICLE_RESPONSES);
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private ArticleService articleService;

    @Test
    void getArticles() {
        when(articleRepository.findAll(any(Pageable.class)))
                .thenReturn(ARTICLE_PAGE);

        assertThat(articleService.getArticles(PAGE_REQUEST))
                .isEqualTo(ARTICLE_RESPONSE_PAGE);
    }

    @Test
    void getArticle() {
        when(articleRepository.findById(anyLong()))
                .thenReturn(Optional.of(ARTICLE_1));

        assertThat(articleService.getArticle(ARTICLE_ID_1))
                .isEqualTo(ArticleResponse.from(ARTICLE_1));
    }

    @Test
    void searchArticleByTitle() {
        when(articleRepository.findByTitleContainingOrderByIdDesc(anyString())).thenReturn(ARTICLES);

        assertThat(articleService.searchArticleByTitle(ARTICLE_SEARCH_BY_TITLE_REQUEST))
                .isEqualTo(ARTICLE_SEARCH_RESPONSE);
    }

    @Test
    void searchArticleByContent() {
        when(articleRepository.findByContentContainingOrderByIdDesc(anyString())).thenReturn(ARTICLES);

        assertThat(articleService.searchArticleByContent(ARTICLE_SEARCH_BY_CONTENT_REQUEST))
                .isEqualTo(ARTICLE_SEARCH_RESPONSE);
    }

    @Test
    void searchArticleByMemberId() {
        when(articleRepository.findByMemberIdOrderByIdDesc(anyString())).thenReturn(ARTICLES);

        assertThat(articleService.searchArticleByMemberId(ARTICLE_SEARCH_BY_MEMBER_ID_REQUEST))
                .isEqualTo(ARTICLE_SEARCH_RESPONSE);
    }

    @Test
    void addHit() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(ARTICLE_2));

        int articleHit = ARTICLE_2.getHit();

        articleService.addHit(ARTICLE_2.getId());

        assertThat(ARTICLE_2.getHit())
                .isEqualTo(articleHit + 1);
    }
}