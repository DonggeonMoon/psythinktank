package com.dgmoonlabs.psythinktank.domain.member.dto;

import javax.validation.constraints.NotBlank;

public record MemberUserLevelRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        String memberId,
        int userLevel
) {
}
