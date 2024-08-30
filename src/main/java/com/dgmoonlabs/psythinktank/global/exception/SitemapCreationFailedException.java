package com.dgmoonlabs.psythinktank.global.exception;

public class SitemapCreationFailedException extends RuntimeException {
    public SitemapCreationFailedException(Throwable cause) {
        super("사이트맵 생성을 실패했습니다.", cause);
    }
}
