package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum QueryParameter {
    ID("id");

    private static final StringBuilder QUERY_STRING = new StringBuilder("?");
    private final String text;

    public static String addParameter(QueryParameter queryParameter, Long parameter) {
        return QUERY_STRING + queryParameter.text + "=" + parameter;
    }
}
