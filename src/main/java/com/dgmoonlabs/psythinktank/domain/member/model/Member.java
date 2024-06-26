package com.dgmoonlabs.psythinktank.domain.member.model;

import com.dgmoonlabs.psythinktank.global.constant.LoginTry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
    @Column(name = "register_date", nullable = false)
    private Date createdAt;

    @Column(name = "user_level", nullable = false)
    private int userLevel;

    @Column(name = "login_try_count", nullable = false)
    @ColumnDefault(value = "0")
    private int loginTryCount;

    @Column(name = "last_login_datetime")
    private LocalDateTime lastLoggedInAt;

    @JsonIgnore
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    public void increaseLoginTryCount() {
        this.loginTryCount++;
    }

    public boolean isLocked() {
        return this.loginTryCount >= LoginTry.COUNT_RANGE.getEnd();
    }
}
