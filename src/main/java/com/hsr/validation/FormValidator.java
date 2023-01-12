package com.hsr.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static Map<String, String> validate(BindingResult bindingResult, Locale locale, String... ignoreFields) {
        Map<String, String> errors = new HashMap<>();

        if(!bindingResult.hasErrors()) {
            return errors;
        }

        List<FieldError> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .filter(fieldError -> {
                    return !Set.of(ignoreFields).contains(fieldError.getField());
                })
                .collect(Collectors.toList());

        for(FieldError fieldError : fieldErrors) {
            String message = messageSource.getMessage(fieldError, locale);
            errors.put(fieldError.getField(), message);
        }
        return errors;
    }

}
