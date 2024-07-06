package com.dgmoonlabs.psythinktank.domain.member.dto;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record MemberRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(message = "아이디에는 5 ~ 30자의 영숫자, 특수문자(-, _)만 사용할 수 있습니다.",
                min = 5, max = 30)
        @Pattern(message = "아이디에는 5 ~ 30자의 영숫자, 특수문자(-, _)만 사용할 수 있습니다.",
                regexp = "[A-z0-9\\-_]*")
        String memberId,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(message = "비밀번호에는 8 ~ 16자의 영숫자, 특수문자"
                + "(!, @, #, $, %, ^, &, ^, &, *, -, _, +, =)만 사용할 수 있습니다.",
                min = 8, max = 16)
        @Pattern(message = "비밀번호에는 8 ~ 16자의 영숫자, 특수문자"
                + "(!, @, #, $, %, ^, &, ^, &, *, -, _, +, =)만 사용할 수 있습니다.",
                regexp = "[A-z0-9!@#$%^&*\\-_+=]+")
        String password,
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
                message = "이메일 형식으로 입력해주세요."
        )
        String email,
        int userLevel
) {
    public Member toEntity() {
        return Member.builder()
                .memberId(memberId)
                .password(password)
                .email(email)
                .userLevel(userLevel)
                .build();
    }
}
