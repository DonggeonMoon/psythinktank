package com.dgmoonlabs.psythinktank.global.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ViewName {
    ROOT(""),
    BOARD_LIST("boardList"),
    BOARD("viewBoard"),
    INSERT_BOARD("insertBoard"),
    UPDATE_BOARD("updateBoard"),
    CIRCULAR_LIST("circularList"),
    INSERT_CIRCULAR("insertCircular"),
    LOGIN("login"),
    MANAGER_PAGE("managerPage"),
    JOIN("join"),
    FIND_ID_AND_PASSWORD("findIdAndPw"),
    EDIT_MEMBER_INFO("editMemberInfo"),
    GOOD_BYE("goodBye"),
    STOCK_LIST("stockList"),
    STOCK("viewStock");


    private static final String REDIRECTION_PREFIX = "redirect:/";
    private final String text;

    public String redirect() {
        return REDIRECTION_PREFIX + this.text;
    }

    public String getText() {
        return text;
    }
}
