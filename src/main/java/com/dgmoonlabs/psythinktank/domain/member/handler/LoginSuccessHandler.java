package com.dgmoonlabs.psythinktank.domain.member.handler;

import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import com.dgmoonlabs.psythinktank.global.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        memberService.resetLoginTryCount(username);
        request.getSession().setAttribute(
                "member",
                Map.of(
                        "memberId", username,
                        "isAdmin", authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .anyMatch(it -> it.equals(Role.ROLE_ADMIN.getValue())
                                )
                )
        );

        response.addCookie(new Cookie("error", null));
        response.addCookie(new Cookie("loginTryCount", null));
        response.sendRedirect("/boardList");
    }
}
