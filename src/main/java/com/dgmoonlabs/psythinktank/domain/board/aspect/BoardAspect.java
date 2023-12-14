package com.dgmoonlabs.psythinktank.domain.board.aspect;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.domain.board.model.Board;
import com.dgmoonlabs.psythinktank.domain.board.repository.BoardRepository;
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
public class BoardAspect {
    private final BoardRepository boardRepository;

    @Around("execution(* com..BoardController.getModifyBoardForm(..)) && args(id, ..)")
    public Object protectUpdateBoardForm(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        return protectBoard(joinPoint, id);
    }

    @Around("execution(* com..BoardController.updateBoard(..)) && args(boardRequest, ..)")
    public Object protectUpdateBoard(final ProceedingJoinPoint joinPoint, final BoardRequest boardRequest) throws Throwable {
        return protectBoard(joinPoint, boardRequest.id());
    }

    @Around("execution(* com..BoardController.deleteBoard(..)) && args(id, ..)")
    public Object protectDeleteBoard(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        return protectBoard(joinPoint, id);
    }

    private Object protectBoard(final ProceedingJoinPoint joinPoint, final long id) throws Throwable {
        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(board.getMemberId())) {
            throw new UnauthorizedAccessException();
        }
        return joinPoint.proceed();
    }
}
