package com.hsr.domain.book.model.validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class BookValidator {
    private static MessageSource messageSource;
    @Autowired
    public BookValidator(MessageSource messageSource) {
        BookValidator.messageSource = messageSource;
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
