package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum KeyName {
    AJAX_RESPONSE_KEY("result"),
    SESSION_KEY("member"),
    BOARDS_KEY("boards"),
    CIRCULARS_KEY("circulars"),
    COMMENTS_KEY("commentList"),
    MEMBERS_KEY("members"),
    STOCKS_KEY("stocks"),
    BOARD_KEY("board"),
    CIRCULAR_KEY("circular"),
    COMMENT_KEY("comment"),
    MEMBER_KEY("memberInfo"),
    STOCK_KEY("stock"),
    HRR_KEY("hrr"),
    SHARE_KEY("share"),
    DIVIDEND_KEY("dividend"),
    GOVERNANCE_KEY("governance"),
    CORPORATE_BOARD_STABILITY("corporateBoardStability"),
    IS_UNIQUE_KEY("isUnique"),
    IS_UNIQUE_KEY2("isUnique2"),
    EXISTS_KEY("exists"),
    ID_KEY("id"),
    IS_SUCCEEDED_KEY("isSucceeded"),
    ERROR_KEY("error"),
    LOGIN_TRY_COUNT_KEY("loginTryCount"),
    MEMBER_ID_KEY("memberId"),
    MEMBER_PASSWORD_KEY("memberPw"),
    MEMBER_EMAIL_KEY("memberEmail"),
    CHART_LABEL_KEY("chartLabel"),
    CHART_DATASET_KEY("chartDataset"),
    STOCK_HYPE_INDEX("stockHypeIndex");

    private final String text;
}