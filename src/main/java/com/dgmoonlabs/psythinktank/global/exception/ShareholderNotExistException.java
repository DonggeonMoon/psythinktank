package com.dgmoonlabs.psythinktank.global.exception;

public class ShareholderNotExistException extends RuntimeException {
    public ShareholderNotExistException() {
        super("주주가 존재하지 않습니다.");
    }
}
