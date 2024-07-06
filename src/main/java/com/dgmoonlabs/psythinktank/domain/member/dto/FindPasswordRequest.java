package com.dgmoonlabs.psythinktank.domain.member.dto;

import javax.validation.constraints.NotBlank;

public record FindPasswordRequest(
        @NotBlank(message = "이메일을 입력해주세요.") String memberEmail,
        @NotBlank(message = "아이디를 입력해주세요.") String memberId
) {
}
