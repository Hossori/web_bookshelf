package com.hsr.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsr.constant.PathConst;
import com.hsr.util.AuthChecker;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(AuthChecker.isLogin(request)) {
            redirectAttributes.addAttribute("page", 0);
            return "redirect:" + PathConst.BOOKSHELF_INDEX.getValue();
        }
        return PathConst.AUTH_LOGIN.getValue();
    }

    @GetMapping("/signup")
    public String signup(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(AuthChecker.isLogin(request)) {
            redirectAttributes.addAttribute("page", 0);
            return "redirect:" + PathConst.BOOKSHELF_INDEX.getValue();
        }
        return PathConst.AUTH_SIGNUP.getValue();
    }

}
