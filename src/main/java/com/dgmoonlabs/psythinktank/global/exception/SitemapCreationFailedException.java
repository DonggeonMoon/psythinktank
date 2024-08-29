package com.dgmoonlabs.psythinktank.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SitemapCreationFailedException extends RuntimeException {
    public SitemapCreationFailedException(Throwable cause) {
        super("사이트맵 생성을 실패했습니다.", cause);
    }
}
