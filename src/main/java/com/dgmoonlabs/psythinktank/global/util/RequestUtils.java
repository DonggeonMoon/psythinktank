package com.dgmoonlabs.psythinktank.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {
    public static final String SEPARATOR_1 = "://";
    public static final String SEPARATOR_2 = ":";

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String baseUrl = scheme + SEPARATOR_1 + serverName + SEPARATOR_2 + port;

        if ((scheme.equals("http") && port == 80) || (scheme.equals("https") && port == 443)) {
            baseUrl = scheme + SEPARATOR_1 + serverName;
        }
        return baseUrl;
    }
}
