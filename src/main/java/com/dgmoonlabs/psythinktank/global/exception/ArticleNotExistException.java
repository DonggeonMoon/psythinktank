package com.dgmoonlabs.psythinktank.global.exception;

public class ArticleNotExistException extends RuntimeException {
    public ArticleNotExistException() {
        super("게시글이 존재하지 않습니다.");
    }
}
