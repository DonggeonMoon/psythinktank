package com.dgmoonlabs.psythinktank.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleArgumentException(IllegalArgumentException e) {
        log.debug(e.getMessage());
    }
}
