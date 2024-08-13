package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiName {
    ARTICLE("articles"),
    CIRCULAR("circulars"),
    LOGIN("login"),
    MEMBERS("members"),
    GOOD_BYE("members/goodBye");

    private static final String REDIRECTION_PREFIX = "redirect:/";
    private final String text;

    public String redirect() {
        return REDIRECTION_PREFIX + this.text;
    }
}
