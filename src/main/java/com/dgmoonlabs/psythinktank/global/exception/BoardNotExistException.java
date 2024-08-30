package com.dgmoonlabs.psythinktank.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BoardNotExistException extends RuntimeException {
    public BoardNotExistException() {
        super("게시판이 존재하지 않습니다.");
    }
}
