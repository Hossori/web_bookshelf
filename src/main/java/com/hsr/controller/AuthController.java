package com.hsr.controller;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hsr.constant.PathConst;
import com.hsr.domain.user.model.User;
import com.hsr.domain.user.model.validator.UserValidator;
import com.hsr.domain.user.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return PathConst.AUTH_LOGIN.getValue();
    }

    @GetMapping("/signup")
    public String signup() {
        return PathConst.AUTH_SIGNUP.getValue();
    }

    @PostMapping("/signup")
    public String signup(
            Model model,
            @ModelAttribute @Validated User user,
            BindingResult bindingResult,
            Locale locale) {

        Map<String, String> errors = UserValidator.validate(bindingResult, locale);
        if(errors != null) {
            model.addAttribute(errors);
            return PathConst.AUTH_SIGNUP.getValue();
        }

        userService.signup(user);
        return PathConst.AUTH_LOGIN.getValue();
    }

}
