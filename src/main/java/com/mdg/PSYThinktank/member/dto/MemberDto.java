package com.mdg.PSYThinktank.member.dto;

import com.mdg.PSYThinktank.member.enums.UserLevel;
import java.sql.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
