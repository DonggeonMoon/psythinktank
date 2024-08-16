package com.dgmoonlabs.psythinktank.global.exception;

public class FileDownloadException extends RuntimeException {
    public FileDownloadException(Throwable cause) {
        super("파일을 다운로드하는 데 실패하였습니다.", cause);
    }
}
