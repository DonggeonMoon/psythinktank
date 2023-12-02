package com.dgmoonlabs.psythinktank.domain.member.dto;

public record FindPasswordResponse(
        boolean exists
) {
    public static FindPasswordResponse from(final boolean exists) {
        return new FindPasswordResponse(exists);
    }
}
