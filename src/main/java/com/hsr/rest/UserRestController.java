package com.hsr.rest;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.domain.user.form.UserEditForm;
import com.hsr.domain.user.model.User;
import com.hsr.domain.user.model.converter.UserConverter;
import com.hsr.domain.user.service.UserService;
import com.hsr.validation.FormValidator;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public Result update(
            @ModelAttribute @Validated UserEditForm userEditForm,
            BindingResult bindingResult,
            Locale locale,
            @AuthenticationPrincipal User loginUser) {
        User user = userService.getById(userEditForm.getId());
        User newUser = UserConverter.toModel(userEditForm);

        HttpStatus httpStatus;
        if(loginUser.equals(user)) {
            Map<String, String> errors;
            if(userEditForm.getEmail().equals(user.getEmail())) {
                errors = FormValidator.validate(bindingResult, locale, "email");
            } else {
                errors = FormValidator.validate(bindingResult, locale);
            }
            if(!errors.isEmpty()) {
                httpStatus = HttpStatus.BAD_REQUEST;
                return new Result(httpStatus.value(), errors);
            }

            httpStatus = userService.update(user, newUser);
        } else {
            httpStatus = HttpStatus.FORBIDDEN;
        }

        return new Result(httpStatus.value(), null);

    }
}
