package com.dgmoonlabs.psythinktank.global.exception;

public class FileSaveFailedException extends RuntimeException {
    public FileSaveFailedException(Throwable cause) {
        super("파일을 저장하는 데 실패하였습니다.", cause);
    }
}
