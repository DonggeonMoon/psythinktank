package com.dgmoonlabs.psythinktank.domain.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record MemberUserLevelRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        String memberId,
        @Size(message = "사용자 등급이 잘못되었습니다.", min = 1, max = 3)
        int userLevel
) {
}
