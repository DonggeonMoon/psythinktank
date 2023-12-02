package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Pagination {
    SIZE(10),
    MEMBER_SIZE(50);

    private final int value;
}
