package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Getter
public enum DateTimeFormat {
    DATE_TIME(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    private final DateTimeFormatter formatter;
}
