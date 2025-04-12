package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BoardStability {
    BUSINESS_YEAR("2024"),
    REPORT_CODE("11011");

    private final String text;
}
