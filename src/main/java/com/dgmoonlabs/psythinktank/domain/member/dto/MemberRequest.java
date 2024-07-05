package com.dgmoonlabs.psythinktank.domain.member.dto;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;

import java.time.LocalDate;

public record MemberRequest(
        String memberId,
        String password,
        String email,
        LocalDate createdAt,
        int userLevel
) {
    public Member toEntity() {
        return Member.builder()
                .memberId(memberId)
                .password(password)
                .email(email)
                .createdAt(createdAt)
                .userLevel(userLevel)
                .build();
    }
}
