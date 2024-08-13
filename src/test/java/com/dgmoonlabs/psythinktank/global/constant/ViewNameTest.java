package com.dgmoonlabs.psythinktank.global.constant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViewNameTest {
    public static final String REDIRECT_PREFIX = "redirect:/";

    @Test
    void redirect() {
        assertThat(ViewName.ARTICLE.redirect())
                .isEqualTo(REDIRECT_PREFIX + ViewName.ARTICLE.getText());
    }
}