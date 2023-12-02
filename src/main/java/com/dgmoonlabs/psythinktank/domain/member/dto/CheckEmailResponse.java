package com.dgmoonlabs.psythinktank.domain.member.dto;

public record CheckEmailResponse(
        boolean isUnique2
) {
    public static CheckEmailResponse from(boolean isUnique2) {
        return new CheckEmailResponse(isUnique2);
    }
}
