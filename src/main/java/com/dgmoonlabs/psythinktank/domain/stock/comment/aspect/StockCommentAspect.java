package com.dgmoonlabs.psythinktank.domain.stock.comment.aspect;

import com.dgmoonlabs.psythinktank.domain.stock.comment.dto.StockCommentRequest;
import com.dgmoonlabs.psythinktank.domain.stock.comment.model.StockComment;
import com.dgmoonlabs.psythinktank.domain.stock.comment.repository.StockCommentRepository;
import com.dgmoonlabs.psythinktank.global.exception.CommentNotExistException;
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
public class StockCommentAspect {
    private final StockCommentRepository stockCommentRepository;

    @Around("execution(* com..StockCommentRestController.updateComment(..)) && args(stockCommentRequest)")
    public Object protectUpdateComment(final ProceedingJoinPoint joinPoint, final StockCommentRequest stockCommentRequest) throws Throwable {
        return protectComment(joinPoint, stockCommentRequest.id());
    }

    @Around("execution(* com..StockCommentRestController.deleteComment(..)) && args(id)")
    public Object protectDeleteComment(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        return protectComment(joinPoint, id);
    }

    private Object protectComment(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        StockComment comment = stockCommentRepository.findById(id).orElseThrow(CommentNotExistException::new);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(comment.getMemberId())) {
            throw new UnauthorizedAccessException();
        }
        return joinPoint.proceed();
    }
}
