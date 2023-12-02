package com.dgmoonlabs.psythinktank.domain.member.model;

import com.dgmoonlabs.psythinktank.domain.member.dto.MemberResponse;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Member implements Serializable {
    @Id
    @Column(name = "member_id", nullable = false, length = 50)
    private String memberId;

    @Column(name = "member_pw", nullable = false, length = 100)
    private String password;

    @Column(name = "member_email", nullable = false, length = 100)
    private String email;

    @CreationTimestamp
    @Column(name = "register_date", nullable = false, length = 50)
    private Date createdAt;

    @Column(name = "user_level", nullable = false)
    private int userLevel;

    @Column(name = "login_try_count")
    @ColumnDefault(value = "0")
    private int loginTryCount;

    public MemberResponse toDto() {
        return MemberResponse.builder()
                .memberId(memberId)
                .password(password)
                .email(email)
                .createdAt(createdAt)
                .userLevel(userLevel)
                .loginTryCount(loginTryCount)
                .build();
    }

    public void increaseLoginTryCount() {
        this.loginTryCount++;
    }
}
