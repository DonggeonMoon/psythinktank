package com.dgmoonlabs.psythinktank.global.handler;

import com.dgmoonlabs.psythinktank.global.exception.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.dgmoonlabs.psythinktank.global.constant.ViewName.BOARD_LIST;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleArgumentException(IllegalArgumentException exception) {
        log.debug(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedAccessException() {
        return BOARD_LIST.redirect();
    }
}
