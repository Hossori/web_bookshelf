package com.hsr.aspect;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.hsr.domain.user.model.User;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("loginUser")
    public User addLoginUser(@AuthenticationPrincipal User loginUser) {
        return loginUser;
    }

}