package com.dgmoonlabs.psythinktank.domain.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserLevel {
    USER(1),
    MANAGER(2),
    ADMIN(3);

    private final int level;

    public boolean isSame(int level) {
        return this.level == level;
    }
}
