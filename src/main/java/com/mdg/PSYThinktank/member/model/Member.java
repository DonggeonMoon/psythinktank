package com.mdg.PSYThinktank.member.model;

import com.mdg.PSYThinktank.member.dto.MemberDto;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Member {
    @Id
    @Column(name = "member_id", nullable = false, length = 50)
    private String memberId;

    @Column(name = "member_pw", nullable = false, length = 100)
    private String memberPw;

    @Column(name = "member_email", nullable = false, length = 100)
    private String memberEmail;

    @CreationTimestamp
    @Column(name = "register_date", nullable = false, length = 50)
    private Date registerDate;

    @Column(name = "user_level", nullable = false)
    private int userLevel;

    @Column(name = "login_try_count")
    @ColumnDefault(value = "0")
    private int loginTryCount;

    public MemberDto toDto() {
        return MemberDto.builder()
                .memberId(memberId)
                .memberPw(memberPw)
                .memberEmail(memberEmail)
                .registerDate(registerDate)
                .userLevel(userLevel)
                .loginTryCount(loginTryCount)
                .build();
    }
}
