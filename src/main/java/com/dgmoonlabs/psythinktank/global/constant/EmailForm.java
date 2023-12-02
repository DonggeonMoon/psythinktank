package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmailForm {
    SENDER_ADDRESS("officialpsythinktank@gmail.com"),
    EMAIL_AUTHENTICATION_TITLE("PSYThinktank 이메일 인증 메일입니다."),
    TEMPORARY_PASSWORD_TITLE("임시 비밀번호를 보내드립니다."),
    TEMPORARY_PASSWORD_TEXT("임시비밀번호는 %s입니다.");

    private final String text;
}
