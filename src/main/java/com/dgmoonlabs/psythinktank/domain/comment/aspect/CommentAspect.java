package com.dgmoonlabs.psythinktank.domain.comment.aspect;

import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentRequest;
import com.dgmoonlabs.psythinktank.domain.comment.model.Comment;
import com.dgmoonlabs.psythinktank.domain.comment.repository.CommentRepository;
import com.dgmoonlabs.psythinktank.global.exception.CommentNotExistException;
import com.dgmoonlabs.psythinktank.global.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CommentAspect {
    private final CommentRepository commentRepository;

    @Around("execution(* com..CommentRestController.createComment(..))")
    public Object protectCreateComment(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken
                || !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new UnauthorizedAccessException();
        }
        return joinPoint.proceed();
    }

    @Around("execution(* com..CommentRestController.updateComment(..)) && args(commentRequest)")
    public Object protectUpdateComment(final ProceedingJoinPoint joinPoint, final CommentRequest commentRequest) throws Throwable {
        return protectComment(joinPoint, commentRequest.id());
    }

    @Around("execution(* com..CommentRestController.deleteComment(..)) && args(id)")
    public Object protectDeleteComment(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        return protectComment(joinPoint, id);
    }

    private Object protectComment(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotExistException::new);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(comment.getMemberId())) {
            throw new UnauthorizedAccessException();
        }
        return joinPoint.proceed();
    }
}
