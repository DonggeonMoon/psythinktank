package com.dgmoonlabs.psythinktank.domain.member.dto;

import javax.validation.constraints.NotBlank;

public record FindIdRequest(
        @NotBlank(message = "이메일을 입력해주세요.") String memberEmail
) {
}
