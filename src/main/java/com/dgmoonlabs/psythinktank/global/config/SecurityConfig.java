package com.dgmoonlabs.psythinktank.global.config;

import com.dgmoonlabs.psythinktank.domain.member.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .antMatchers(
                                "/managerPage",
                                "/changeUserLeve"
                        )
                        .hasRole("ADMIN")
                        .antMatchers(
                                "/",
                                "/circularList",
                                "/circular",
                                "/member",
                                "/login",
                                "/logout",
                                "/assets/**",
                                "/build/**",
                                "/error/**",
                                "/web/**"
                        )
                        .permitAll()
                        .antMatchers(
                                "/favicon.ico",
                                "/sitemap.xml",
                                "/style.css"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .csrf().disable()
                .formLogin(configurer -> configurer
                        .loginPage("/login")
                        .loginProcessingUrl("/login_proc")
                        .usernameParameter("memberId")
                        .passwordParameter("memberPw")
                        .successHandler(loginSuccessHandler)
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
