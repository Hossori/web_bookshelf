package com.hsr.validation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class FormValidator {
    private static MessageSource messageSource;
    @Autowired
    public FormValidator(MessageSource messageSource) {
        FormValidator.messageSource = messageSource;
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
