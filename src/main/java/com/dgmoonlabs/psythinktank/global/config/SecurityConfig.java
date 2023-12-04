package com.dgmoonlabs.psythinktank.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .antMatchers("/managerPage")
                        .hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/boardList")
                        .authenticated()
                        .anyRequest()
                        .permitAll()
                )
                .csrf().disable()
                .formLogin(configurer -> configurer
                        .loginPage("/login")
                        .defaultSuccessUrl("/boardList")
                        .usernameParameter("memberId")
                        .passwordParameter("memberPw")
                        .loginProcessingUrl("/login_proc")
                );
        return http.build();
    }

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
