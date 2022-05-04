package com.hsr.domain.user.model.validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class UserValidator {

    private static MessageSource messageSource;
    @Autowired
    public UserValidator(MessageSource messageSource) {
        UserValidator.messageSource = messageSource;
    }

    public static Map<String, String> validate(BindingResult bindingResult, Locale locale) {
        Map<String, String> errors = null;
        if(bindingResult.hasErrors()) {
            errors = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                String message = messageSource.getMessage(error, locale);
                errors.put(error.getField(), message);
            }
        }
        return errors;
    }

}
