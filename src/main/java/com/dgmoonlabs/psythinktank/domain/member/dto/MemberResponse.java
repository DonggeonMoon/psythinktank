package com.dgmoonlabs.psythinktank.domain.member.dto;

import com.dgmoonlabs.psythinktank.domain.member.constant.UserLevel;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;

import java.sql.Date;

public record MemberResponse(
        String memberId,
        String password,
        String email,
        Date createdAt,
        int userLevel,
        int loginTryCount
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getMemberId(),
                member.getPassword(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getUserLevel(),
                member.getLoginTryCount()
        );
    }

    public boolean isAdmin() {
        return userLevel == UserLevel.ADMIN.getLevel();
    }
}
