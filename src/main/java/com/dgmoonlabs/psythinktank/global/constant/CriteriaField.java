package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CriteriaField {
    ID("id"),
    IS_NOTICE("isNotice"),
    USER_LEVEL("userLevel"),
    SYMBOL("symbol");

    private final String name;
}
