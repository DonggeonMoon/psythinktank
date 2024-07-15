package com.dgmoonlabs.psythinktank.global.constant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParameterTest {
    public static final long ID_VALUE = 1L;
    public static final String ADD_PARAMETER_SUCCESS_RESULT = "?id=1";

    @Test
    void addParameter() {
        assertThat(QueryParameter.addParameter(QueryParameter.ID, ID_VALUE))
                .isEqualTo(ADD_PARAMETER_SUCCESS_RESULT);
    }
}