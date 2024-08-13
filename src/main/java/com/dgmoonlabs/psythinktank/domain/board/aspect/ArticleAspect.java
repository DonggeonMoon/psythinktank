package com.dgmoonlabs.psythinktank.domain.board.aspect;

import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleRequest;
import com.dgmoonlabs.psythinktank.domain.board.model.Article;
import com.dgmoonlabs.psythinktank.domain.board.repository.ArticleRepository;
import com.dgmoonlabs.psythinktank.global.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ArticleAspect {
    private final ArticleRepository articleRepository;

    @Around("execution(* com..AticleController.getAddArticleForm(..)) && args(id, ..)")
    public Object protectAddArticleForm(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        return protectArticle(joinPoint, id);
    }

    @Around("execution(* com..ArticleController.getModifyArticleForm(..)) && args(id, ..)")
    public Object protectUpdateArticleForm(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        return protectArticle(joinPoint, id);
    }

    @Around("execution(* com..ArticleController.updateArticle(..)) && args(articleRequest, ..)")
    public Object protectUpdateArticle(final ProceedingJoinPoint joinPoint, final ArticleRequest articleRequest) throws Throwable {
        return protectArticle(joinPoint, articleRequest.id());
    }

    @Around("execution(* com..ArticleController.deleteArticle(..)) && args(id, ..)")
    public Object protectDeleteArticle(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        return protectArticle(joinPoint, id);
    }

    private Object protectArticle(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        Article article = articleRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(article.getMemberId())) {
            throw new UnauthorizedAccessException();
        }
        return joinPoint.proceed();
    }
}
