package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LoginResult {
    BLANK_ID_AND_PASSWORD(1),
    ABSENT_ID(2),
    WRONG_PASSWORD(3),
    LOGIN_TRY_EXCEEDING(4),
    SUCCESS(-1);

    private final int code;
}
