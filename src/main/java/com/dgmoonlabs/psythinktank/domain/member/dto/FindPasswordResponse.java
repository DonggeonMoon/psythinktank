package com.dgmoonlabs.psythinktank.domain.member.dto;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;

public record FindPasswordResponse(
        boolean exists
) {
    public static FindPasswordResponse from(final Member member) {
        return new FindPasswordResponse(member != null);
    }
}
