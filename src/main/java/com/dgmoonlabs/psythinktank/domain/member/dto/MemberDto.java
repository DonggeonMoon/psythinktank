package com.dgmoonlabs.psythinktank.domain.member.dto;

import com.dgmoonlabs.psythinktank.domain.member.constant.UserLevel;
import lombok.*;

import java.sql.Date;

@Builder
@NoArgsConstructor(access = AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberDto {
    private String memberId;
    private String password;
    private String email;
    private Date createdAt;
    private int userLevel;
    private int loginTryCount;

    public boolean isAdmin() {
        return userLevel == UserLevel.ADMIN.getLevel();
    }
}
