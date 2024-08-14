package com.dgmoonlabs.psythinktank.global.config;

import com.dgmoonlabs.psythinktank.domain.member.handler.LoginFailureHandler;
import com.dgmoonlabs.psythinktank.domain.member.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .antMatchers(
                                HttpMethod.GET, "/members"
                        )
                        .hasRole("ADMIN")
                        .antMatchers(
                                HttpMethod.PUT, "/api/members/change/userLevel"
                        )
                        .hasRole("ADMIN")
                        .antMatchers(
                                HttpMethod.DELETE, "/api/circulars"
                        )
                        .hasRole("ADMIN")
                        .antMatchers(
                                HttpMethod.GET,
                                "/articles/add",
                                "/articles/modify/**",
                                "/chat"
                        ).authenticated()
                        .antMatchers(
                                "/",
                                "/articles/**",
                                "/api/articles/search/title",
                                "/api/articles/search/content",
                                "/api/articles/search/memberId",
                                "/boards/**/articles",
                                "/stocks/**",
                                "/api/stocks/search/symbol",
                                "/api/stocks/search/stockName",
                                "/circulars/**",
                                "/api/circulars/**",
                                "/contents/**",
                                "/members/**",
                                "/login",
                                "/logout",
                                "/assets/**",
                                "/build/**",
                                "/error/**",
                                "/js/**",
                                "/web/**",
                                "/members/findIdAndPw",
                                "/api/members/find/id",
                                "/api/members/find/password",
                                "/api/members/check/id",
                                "/api/members/check/email",
                                "/members/goodBye"
                        )
                        .permitAll()
                        .antMatchers(
                                "/favicon.ico",
                                "/robots.txt",
                                "/sitemap.xml",
                                "/style.css"
                        )
                        .permitAll()
                        .antMatchers("/supervision/**")
                        .access((authentication, object) -> {
                            if ("127.0.0.1".equals(object.getRequest().getRemoteAddr())) {
                                return new AuthorizationDecision(true);
                            }
                            return new AuthorizationDecision(false);
                        })
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
                        .failureHandler(loginFailureHandler)
                );
        return http.build();
    }
}
