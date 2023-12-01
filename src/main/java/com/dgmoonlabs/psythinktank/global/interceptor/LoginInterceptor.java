package com.dgmoonlabs.psythinktank.global.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.SESSION_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.LOGIN;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        if (emptySessionFrom(request)) {
            response.sendRedirect(request.getContextPath() + "/" + LOGIN.getText());
            return false;
        }
        return true;
    }

    private boolean emptySessionFrom(final HttpServletRequest request) {
        return getSessionFrom(request).getAttribute(SESSION_KEY.getText()) == null;
    }

    private HttpSession getSessionFrom(final HttpServletRequest request) {
        return request.getSession();
    }
}
