package com.dgmoonlabs.psythinktank.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CircularNotExistException extends RuntimeException {
    public CircularNotExistException() {
        super("회보가 존재하지 않습니다.");
    }
}
