package com.dgmoonlabs.psythinktank.domain.member.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public record MemberUserLevelRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        String memberId,
        @Min(message = "사용자 등급이 잘못되었습니다.", value = 1)
        @Max(message = "사용자 등급이 잘못되었습니다.", value = 3)
        int userLevel
) {
}
