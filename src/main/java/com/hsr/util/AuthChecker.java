package com.hsr.util;

import javax.servlet.http.HttpServletRequest;

public class AuthChecker {
    public static boolean isLogin(HttpServletRequest request) {
        return request.getSession().getAttribute("loginUser") != null;
    }
}
