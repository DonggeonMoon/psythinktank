package com.dgmoonlabs.psythinktank.global.interceptor;

import com.dgmoonlabs.psythinktank.global.constant.Role;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull final HttpServletResponse response, @NonNull final Object handler, ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                System.out.println(authority.getAuthority());

            }

            if (modelAndView != null) {
                modelAndView.getModelMap().put("member", Map.of(
                        "memberId", authentication.getName(),
                        "isAdmin", authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .anyMatch(it -> it.equals(Role.ROLE_ADMIN.getValue()))
                ));
            }
        }
    }
}
