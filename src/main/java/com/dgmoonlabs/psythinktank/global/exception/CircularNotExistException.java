package com.dgmoonlabs.psythinktank.global.exception;

public class CircularNotExistException extends RuntimeException {
    public CircularNotExistException() {
        super("회보가 존재하지 않습니다.");
    }
}
