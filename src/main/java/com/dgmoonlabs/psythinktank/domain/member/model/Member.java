package com.dgmoonlabs.psythinktank.domain.member.model;

import com.dgmoonlabs.psythinktank.global.constant.LoginTry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Member implements Serializable {
    @Id
    @Column(name = "member_id", nullable = false, length = 50)
    @Comment("아이디")
    private String memberId;

    @Column(name = "member_pw", nullable = false, length = 100)
    @Comment("비밀번호")
    private String password;

    @Column(name = "member_email", nullable = false, length = 100)
    @Comment("이메일")
    private String email;

    @CreationTimestamp
    @Column(name = "register_date", nullable = false)
    @Comment("가입일")
    private LocalDate createdAt;

    @Column(name = "user_level", nullable = false)
    @Comment("사용자 등급")
    private int userLevel;

    @Column(name = "login_try_count", nullable = false)
    @ColumnDefault(value = "0")
    @Comment("로그인 시도 횟수")
    private int loginTryCount;

    @Column(name = "last_login_datetime")
    @Comment("마지막 로그인 일시")
    private LocalDateTime lastLoggedInAt;

    @Column(name = "last_password_change_datetime")
    @Comment("마지막 비밀번호 변경일시")
    private LocalDateTime passwordLastChangedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    public void increaseLoginTryCount() {
        this.loginTryCount++;
    }

    public void resetLoginTryCount() {
        this.loginTryCount = LoginTry.COUNT_RANGE.getStart();
    }

    public void changePassword(String password) {
        this.password = password;
        this.passwordLastChangedAt = LocalDateTime.now();
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public void updateLastLoggedInAt() {
        this.lastLoggedInAt = LocalDateTime.now();
    }

    public boolean isLocked() {
        return this.loginTryCount >= LoginTry.COUNT_RANGE.getEnd();
    }
}
