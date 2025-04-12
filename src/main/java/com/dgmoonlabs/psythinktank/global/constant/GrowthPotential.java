package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GrowthPotential {
    BUSINESS_YEAR("2024"),
    PREVIOUS_BUSINESS_YEAR("2023"),
    REPORT_CODE("11011");

    private final String text;
}
