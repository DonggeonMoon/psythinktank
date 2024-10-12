package com.dgmoonlabs.psythinktank.domain.member.model;

import com.dgmoonlabs.psythinktank.global.constant.LoginTry;
import com.dgmoonlabs.psythinktank.global.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false, length = 50)
    private String memberId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "user_level", nullable = false)
    private int userLevel;

    @Column(name = "login_try_count", nullable = false)
    @ColumnDefault(value = "0")
    private int loginTryCount;

    @Column(name = "last_logged_in_at")
    private LocalDateTime lastLoggedInAt;

    @Column(name = "password_last_changed_at")
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

    public void updateLastLoggedIn() {
        this.lastLoggedInAt = LocalDateTime.now();
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public boolean isLocked() {
        return this.loginTryCount >= LoginTry.COUNT_RANGE.getEnd();
    }
}
