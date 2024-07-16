package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StockHypeIndex {
    BUSINESS_YEAR("2023"),
    LAST_BUSINESS_YEAR("2022"),
    REPORT_CODE("11011");

    private final String text;
}
