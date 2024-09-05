package com.dgmoonlabs.psythinktank.global.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class I18nInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response, @NonNull final Object handler, final ModelAndView modelAndView) {
        if (modelAndView != null) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
            resourceBundle.keySet()
                    .forEach(key -> modelAndView.addObject(
                                    key,
                                    resourceBundle.getString(key)
                            )
                    );
            modelAndView.addObject("requestUrl", request.getRequestURL());
            modelAndView.addObject("locale", resourceBundle.getLocale());
        }
    }
}
