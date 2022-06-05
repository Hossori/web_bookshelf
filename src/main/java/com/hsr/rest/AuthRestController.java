package com.hsr.rest;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.domain.user.model.User;
import com.hsr.domain.user.model.validator.UserValidator;
import com.hsr.domain.user.service.UserService;

@RestController
@RequestMapping("/rest/auth")
public class AuthRestController {

    @Autowired
    private UserService userService;

    @PutMapping("/signup")
    public Result signup(
            Model model,
            @ModelAttribute @Validated User user,
            BindingResult bindingResult,
            Locale locale) {
        Map<String, String> errors = UserValidator.validate(bindingResult, locale);
        if(errors != null) {
            return new Result(HttpStatus.BAD_REQUEST.value(), errors);
        }

        userService.signup(user);
        return new Result(HttpStatus.OK.value(), null);
    }

}
