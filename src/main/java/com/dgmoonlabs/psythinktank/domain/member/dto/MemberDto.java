package com.dgmoonlabs.psythinktank.domain.member.dto;

import com.dgmoonlabs.psythinktank.domain.member.enums.UserLevel;
import lombok.*;

import java.sql.Date;

@Builder
@NoArgsConstructor(access = AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberDto {
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private Date registerDate;
    private int userLevel;
    private int loginTryCount;

    public boolean isAdmin() {
        return userLevel == UserLevel.ADMIN.getLevel();
    }
}
