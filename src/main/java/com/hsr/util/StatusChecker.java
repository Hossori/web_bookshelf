package com.hsr.util;

import org.springframework.ui.Model;

public class StatusChecker {
    public static boolean isLogin(Model model) {
        return model.getAttribute("loginUser") != null;
    }
}
