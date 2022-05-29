package com.hsr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsr.constant.PathConst;
import com.hsr.domain.user.model.User;
import com.hsr.util.StatusChecker;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Model model, RedirectAttributes redirectAttributes) {
        if(StatusChecker.isLogin(model)) {
            redirectAttributes.addAttribute("page", 0);
            return "redirect:/bookshelf/index";
        }
        model.addAttribute("user", new User());
        return PathConst.AUTH_LOGIN.getValue();
    }

    @GetMapping("/signup")
    public String signup(Model model, RedirectAttributes redirectAttributes) {
        if(StatusChecker.isLogin(model)) {
            redirectAttributes.addAttribute("page", 0);
            return "redirect:/bookshelf/index";
        }
        return PathConst.AUTH_SIGNUP.getValue();
    }

}
