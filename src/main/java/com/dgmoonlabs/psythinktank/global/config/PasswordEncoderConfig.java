package com.dgmoonlabs.psythinktank.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(final CharSequence rawPassword) {
                return BCrypt.hashpw(String.valueOf(rawPassword), BCrypt.gensalt());
            }

            @Override
            public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
                return BCrypt.checkpw(String.valueOf(rawPassword), encodedPassword);
            }
        };
    }
}
