package com.dgmoonlabs.psythinktank.domain.member.handler;

import com.dgmoonlabs.psythinktank.domain.member.dto.MemberResponse;
import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import com.dgmoonlabs.psythinktank.global.constant.LoginResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final MemberService memberService;

    private static String convertErrorMessage(final String message) {
        return message.replaceAll("\\D", "");
    }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException) {
            response.addCookie(new Cookie("error", String.valueOf(LoginResult.WRONG_PASSWORD.getCode())));

        }
        if (exception instanceof InternalAuthenticationServiceException) {
            response.addCookie(new Cookie("error", convertErrorMessage(exception.getMessage())));
        }
        String memberId = request.getParameter("memberId");
        memberService.increaseLoginTryCount(memberId);
        if (!String.valueOf(LoginResult.ABSENT_ID.getCode()).equals(exception.getMessage())
                && !String.valueOf(LoginResult.BLANK_ID_AND_PASSWORD.getCode()).equals(exception.getMessage())
        ) {
            MemberResponse memberResponse = memberService.getMember(memberId);
            response.addCookie(new Cookie("loginTryCount", String.valueOf(memberResponse.loginTryCount())));
        }
        response.sendRedirect("/login");
    }
}
