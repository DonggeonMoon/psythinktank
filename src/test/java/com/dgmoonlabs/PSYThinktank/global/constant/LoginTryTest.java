package com.dgmoonlabs.PSYThinktank.global.constant;

import com.dgmoonlabs.psythinktank.global.constant.LoginTry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginTryTest {
    public static final int COUNT_FAILURE_CASE = -1;
    public static final int COUNT_SUCCESS_CASE = 0;
    public static final int COUNTS_SUCCESS_CASE_2 = 4;
    public static final int COUNT_FAILURE_CASE_2 = 5;

    @Test
    void includes() {
        assertThat(LoginTry.includes(COUNT_FAILURE_CASE))
                .isFalse();
    }

    @Test
    void includes2() {
        assertThat(LoginTry.includes(COUNT_SUCCESS_CASE))
                .isTrue();
    }

    @Test
    void includes3() {
        assertThat(LoginTry.includes(COUNTS_SUCCESS_CASE_2))
                .isTrue();
    }

    @Test
    void includes4() {
        assertThat(LoginTry.includes(COUNT_FAILURE_CASE_2))
                .isFalse();
    }
}