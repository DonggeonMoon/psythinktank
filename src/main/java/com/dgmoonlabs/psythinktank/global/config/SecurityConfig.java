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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

import static com.dgmoonlabs.psythinktank.global.constant.QueryParameter.MEMBER_ID;
import static com.dgmoonlabs.psythinktank.global.constant.QueryParameter.MEMBER_PASSWORD;

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
                                HttpMethod.GET,
                                "/articles/add",
                                "/articles/modify/**",
                                "/chat"
                        ).authenticated()
                        .antMatchers(
                                HttpMethod.POST,
                                "/stocks/download/excel"
                        )
                        .hasRole("ADMIN")
                        .antMatchers(
                                "/",
                                "/articles/**",
                                "/api/articles/search/title",
                                "/api/articles/search/content",
                                "/api/articles/search/memberId",
                                "/api/comments/**",
                                "/api/boards/**/articles",
                                "/stocks/**",
                                "/api/stocks/search/symbol",
                                "/api/stocks/search/stockName",
                                "/api/stocks/comments/**",
                                "/newsletters/**",
                                "/api/newsletters/**",
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
                        ).permitAll()
                        .antMatchers(
                                "/favicon.ico",
                                "/robots.txt",
                                "/sitemap.xml",
                                "/style.css",
                                "/messages.json"
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
                .exceptionHandling(exception -> exception
                        .defaultAuthenticationEntryPointFor(
                                (request, response, object) ->
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"),
                                new AntPathRequestMatcher("/api/**")
                        )
                        .defaultAccessDeniedHandlerFor(
                                (request, response, object) ->
                                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"),
                                new AntPathRequestMatcher("/api/**")
                        )
                )
                .csrf().disable()
                .formLogin(configurer -> configurer
                        .loginPage("/login")
                        .loginProcessingUrl("/login_proc")
                        .usernameParameter(MEMBER_ID.getText())
                        .passwordParameter(MEMBER_PASSWORD.getText())
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                );
        return http.build();
    }
}
