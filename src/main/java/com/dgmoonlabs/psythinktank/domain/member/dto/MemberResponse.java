package com.dgmoonlabs.psythinktank.domain.member.dto;

import com.dgmoonlabs.psythinktank.domain.member.constant.UserLevel;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.global.constant.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

public record MemberResponse(
        String memberId,
        String password,
        String email,
        LocalDate createdAt,
        int userLevel,
        int loginTryCount,
        String lastLoggedInAt
) implements Serializable {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getMemberId(),
                member.getPassword(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getUserLevel(),
                member.getLoginTryCount(),
                (member.getLastLoggedInAt() != null) ? member.getLastLoggedInAt().format(DateTimeFormat.DATE_TIME.getFormatter()) : null
        );
    }

    public boolean isAdmin() {
        return userLevel == UserLevel.ADMIN.getLevel();
    }
}
