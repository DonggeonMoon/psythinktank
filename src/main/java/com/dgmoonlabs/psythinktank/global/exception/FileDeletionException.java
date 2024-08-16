package com.dgmoonlabs.psythinktank.global.exception;

public class FileDeletionException extends RuntimeException {
    public FileDeletionException(Throwable cause) {
        super("파일을 삭제하는 데 실패하였습니다.", cause);
    }
}
