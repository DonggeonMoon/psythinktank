package com.dgmoonlabs.psythinktank.domain.member.model;

import com.dgmoonlabs.psythinktank.global.constant.Role;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role name;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
