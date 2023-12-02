package com.dgmoonlabs.psythinktank.domain.member.dto;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;

public record FindIdResponse(
        boolean exists,
        String id
) {
    public static FindIdResponse from(final Member member) {
        if (member != null) {
            return new FindIdResponse(
                    true,
                    member.getMemberId()
            );
        }
        return new FindIdResponse(
                false,
                null
        );
    }
}
