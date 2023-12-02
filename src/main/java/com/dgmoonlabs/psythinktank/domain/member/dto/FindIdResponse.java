package com.dgmoonlabs.psythinktank.domain.member.dto;

public record FindIdResponse(
        boolean exists,
        String id
) {
    public static FindIdResponse of(boolean exists, String id) {
        return new FindIdResponse(exists, id);
    }
}
