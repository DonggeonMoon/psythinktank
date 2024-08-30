package com.dgmoonlabs.psythinktank.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CommentNotExistException extends RuntimeException {
    public CommentNotExistException() {
        super("댓글이 존재하지 않습니다.");
    }
}
