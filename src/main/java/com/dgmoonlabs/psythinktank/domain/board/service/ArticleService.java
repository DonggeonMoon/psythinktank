package com.dgmoonlabs.psythinktank.domain.board.service;

import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleResponse;
import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleSearchRequest;
import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleSearchResponse;
import com.dgmoonlabs.psythinktank.domain.board.model.Article;
import com.dgmoonlabs.psythinktank.domain.board.repository.ArticleRepository;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public void createArticle(ArticleRequest request) {
        articleRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<ArticleResponse> getBoardArticles(Long boardId, Pageable pageable) {

        return articleRepository.findAllByBoardId(
                PageRequest.of(
                        pageable.getPageNumber(),
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.IS_NOTICE.getName())
                                .descending()
                                .and(Sort.by(CriteriaField.ID.getName())
                                        .descending())
                ),
                boardId
        ).map(ArticleResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<ArticleResponse> getArticles(Pageable pageable) {
        return articleRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.IS_NOTICE.getName())
                                .descending()
                                .and(Sort.by(CriteriaField.ID.getName())
                                        .descending())
                )
        ).map(ArticleResponse::from);
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticle(long id) {
        return ArticleResponse.from(
                articleRepository.findById(id)
                        .orElseThrow(IllegalStateException::new)
        );
    }

    @Transactional(readOnly = true)
    public ArticleSearchResponse searchArticleByTitle(ArticleSearchRequest articleSearchRequest) {
        return new ArticleSearchResponse(
                articleRepository
                        .findByTitleContainingOrderByIdDesc(articleSearchRequest.searchText())
                        .stream()
                        .map(ArticleResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public ArticleSearchResponse searchArticleByContent(ArticleSearchRequest articleSearchRequest) {
        return new ArticleSearchResponse(
                articleRepository.findByContentContainingOrderByIdDesc(articleSearchRequest.searchText())
                        .stream()
                        .map(ArticleResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public ArticleSearchResponse searchArticleByMemberId(ArticleSearchRequest articleSearchRequest) {
        return new ArticleSearchResponse(
                articleRepository.findByMemberIdOrderByIdDesc(articleSearchRequest.searchText())
                        .stream()
                        .map(ArticleResponse::from)
                        .toList()
        );
    }

    @Transactional
    public void updateArticle(ArticleRequest request) {
        articleRepository.findById(request.id())
                .orElseThrow(IllegalArgumentException::new)
                .update(request);
    }

    @Transactional
    public void deleteArticle(long id) {
        articleRepository.deleteById(id);
    }

    @Transactional
    public void addHit(long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(IllegalStateException::new);
        article.increaseHit();
    }
}
