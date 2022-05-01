package com.hsr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.hsr.constant.PathConst;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return PathConst.AUTH_LOGIN.getValue();
    }

    @GetMapping("/signup")
    public String signup() {
        return PathConst.AUTH_SIGNUP.getValue();
    }

}
