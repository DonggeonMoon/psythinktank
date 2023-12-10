package com.dgmoonlabs.psythinktank.global.handler;

import com.dgmoonlabs.psythinktank.global.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.dgmoonlabs.psythinktank.global.constant.ViewName.BOARD_LIST;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleArgumentException(IllegalArgumentException exception) {
        log.debug(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccessException() {
        return BOARD_LIST.redirect();
    }
}
