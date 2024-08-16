package com.dgmoonlabs.psythinktank.global.exception;

public class BoardNotExistException extends RuntimeException {
    public BoardNotExistException() {
        super("게시판이 존재하지 않습니다.");
    }
}
