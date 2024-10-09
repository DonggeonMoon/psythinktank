package com.dgmoonlabs.psythinktank.global.constant.opendart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiName {
    DIVIDEND("alotMatter"),
    EMPLOYEE("empSttus"),
    DIRECTOR_COMPENSATION("hmvAuditAllSttus"),
    UNREGISTERED_DIRECTOR_COMPENSATION("unrstExctvMendngSttus");

    private final String text;
}
