package com.dgmoonlabs.psythinktank.domain.member.dto;

public record CheckIdResponse(
        boolean isUnique
) {
    public static CheckIdResponse from(boolean isUnique) {
        return new CheckIdResponse(isUnique);
    }
}
