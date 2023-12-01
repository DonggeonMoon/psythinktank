package com.dgmoonlabs.psythinktank.global.interceptor;

import com.dgmoonlabs.psythinktank.global.constant.ViewName;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.SESSION_KEY;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SESSION_KEY.getText()) == null) {
            response.sendRedirect(request.getContextPath() + "/" + ViewName.LOGIN.getText());
            return false;
        } else {
            return true;
        }
    }
}
