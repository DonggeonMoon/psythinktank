package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LoginTry {
    COUNT_RANGE(0, 5);

    private final int start;
    private final int end;

    public static boolean includes(int count) {
        return COUNT_RANGE.start <= count && count < COUNT_RANGE.end;
    }
}
